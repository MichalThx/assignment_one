package com.test.warehouse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.test.warehouse.dto.*;
import com.test.warehouse.entity.ArticleEntity;
import com.test.warehouse.entity.ProductEntity;
import com.test.warehouse.mapper.ObjectConverter;
import com.test.warehouse.repository.ArticleRepository;
import com.test.warehouse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


	@PostMapping("/products")
	public ResponseEntity<String>  updateProducts(@RequestPart("file") String data) throws JsonProcessingException {
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

		}catch(Exception ignored){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PostMapping("/inventory")
	public ResponseEntity<String> updateInventory(@RequestPart("file") String data) throws JsonProcessingException {
		InventoryDTO inventoryDTO = null;
		try{
			inventoryDTO = objectMapper.readValue(data, InventoryDTO.class);
			List<ArticleEntity> updatedArticles = new ArrayList<>();
			inventoryDTO.getInventory().forEach(a -> {
				updatedArticles.add(objectConverter.toEntity(a));

			});
			articleRepository.saveAllAndFlush(updatedArticles);

		}catch(Exception ignored){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping("/list")
	public ResponseEntity<List<ItemsDTO>> getAllProducts() throws IOException {

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
		return ResponseEntity.status(HttpStatus.OK).body(available);
	}

	@PostMapping(value = "/sell")
	public ResponseEntity<Object> sellProduct(@RequestPart("data") String data) throws JsonProcessingException {
		System.out.println(data);
		try{
			OrderDTO[] orderDTOS = objectMapper.readValue(data, OrderDTO[].class);
			for(OrderDTO orderDTO : orderDTOS){
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
					}else{	break; }
				}

				if(updatedArticles.size() == productArticleDTOS.length){
					articleRepository.saveAllAndFlush(updatedArticles);
					orderDTO.setStock(String.valueOf(
							Integer.parseInt(orderDTO.getStock()) - Integer.parseInt(orderDTO.getQuantity())));
				}
			}

			return ResponseEntity.status(HttpStatus.OK).body(orderDTOS);

		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}
