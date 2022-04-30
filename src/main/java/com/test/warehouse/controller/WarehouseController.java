package com.test.warehouse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.test.warehouse.dto.ItemsDTO;
import com.test.warehouse.dto.OrderDTO;
import com.test.warehouse.dto.ProductArticleDTO;
import com.test.warehouse.dto.ProductWrapperDTO;
import com.test.warehouse.entity.ArticleEntity;
import com.test.warehouse.entity.ProductEntity;
import com.test.warehouse.mapper.ObjectConverter;
import com.test.warehouse.repository.ArticleRepository;
import com.test.warehouse.repository.ProductRepository;
import net.bytebuddy.dynamic.NexusAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ArticleRepository articleRepository;

	private ObjectMapper objectMapper;

	private ObjectConverter objectConverter;

	{
		objectMapper = new ObjectMapper();
		objectConverter = new ObjectConverter();
	}


	@PostMapping("/upload")
	public ProductWrapperDTO updateProducts(@RequestPart("data") String data) throws JsonProcessingException {
		ProductWrapperDTO productsWrapped = null;
		try{
			productsWrapped = objectMapper.readValue(data, ProductWrapperDTO.class);
			List<ProductEntity> updatedProducts = new ArrayList<>();
			productsWrapped.getProducts().forEach(a -> {
				try {
					updatedProducts.add(objectConverter.toEntity(a));
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}

			});
			productRepository.saveAllAndFlush(updatedProducts);

		}catch(JsonProcessingException ignored){}

		return productsWrapped;

	}

	@GetMapping("/list")
	public List getAllProducts() throws IOException {

		List<ProductEntity> products = productRepository.findAll();
		HashMap<Integer, Integer> inventoryMap = new HashMap<>();
		articleRepository.findAll().forEach(a -> {
			inventoryMap.put(a.getArtId(), a.getStock());
		});

		List<ItemsDTO> available = new ArrayList<>();


		for(ProductEntity productEntity : products){

			ProductArticleDTO[] productArticleDTOS = objectMapper.readValue(productEntity.getContainArticles(),
					ProductArticleDTO[].class);
			int max = productArticleDTOS.length > 0 ?  Integer.MAX_VALUE : 0;
			for(ProductArticleDTO productArticleDTO : productArticleDTOS){
				int artId = Integer.parseInt(productArticleDTO.getArtId());
				if(inventoryMap.containsKey(artId)){
					int t = inventoryMap.get(artId);
					max = Math.min(max, t / Integer.parseInt(productArticleDTO.getAmountOf()));
				}else{
					max = 0;
				}
			}
			available.add(new ItemsDTO(productEntity.getName(), max));
		}
		return available;
	}

	@PostMapping("/sell")
	public List sellProduct(@RequestPart("data") String data) throws JsonProcessingException {
		try{
			OrderDTO orderDTO = objectMapper.readValue(data, OrderDTO.class);
			ProductEntity productEntity = productRepository.getById(orderDTO.getName());
			ProductArticleDTO[] productArticleDTOS = objectMapper.readValue(productEntity.getContainArticles(),
					ProductArticleDTO[].class);
			List<ArticleEntity> updatedArticles = new ArrayList<>();

			for(ProductArticleDTO productArticleDTO : productArticleDTOS){
				int required = Integer.parseInt(productArticleDTO.getAmountOf()) * Integer.parseInt(orderDTO.getQuantity());
				ArticleEntity articleEntity = articleRepository.getById(Integer.valueOf(productArticleDTO.getArtId()));
				if(required <= articleEntity.getStock()){
					articleEntity.setStock(articleEntity.getStock() - required);
					updatedArticles.add(articleEntity);
				}else{

					break;
				}
			}

			if(updatedArticles.size() == productArticleDTOS.length){
				articleRepository.saveAllAndFlush(updatedArticles);
			}

			return null;

		}catch (Exception e){
			return null;
		}
	}
}
