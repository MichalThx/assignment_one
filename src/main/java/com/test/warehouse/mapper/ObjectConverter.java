package com.test.warehouse.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.warehouse.dto.ArticleDTO;
import com.test.warehouse.dto.ProductArticleDTO;
import com.test.warehouse.dto.ProductDTO;
import com.test.warehouse.entity.ArticleEntity;
import com.test.warehouse.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class ObjectConverter {
	ObjectMapper objectMapper = new ObjectMapper();

	public ArticleEntity toEntity(ArticleDTO articleDto) {
		return new ArticleEntity(Integer.parseInt(articleDto.getArtId()), articleDto.getName(), Integer.parseInt(articleDto.getStock()));
	}

	public ArticleDTO toDto(ArticleEntity articleEntity) {
		return new ArticleDTO(String.valueOf(articleEntity.getArtId()), articleEntity.getName(), String.valueOf(articleEntity.getStock()));
	}

	public ProductArticleDTO toDto(String productArticleEntity) throws JsonProcessingException {
		return objectMapper.readValue(productArticleEntity, ProductArticleDTO.class);
	}

	public ProductEntity toEntity(ProductDTO productDTO) throws JsonProcessingException {


		String json = objectMapper.writeValueAsString(productDTO.getContainArticles());


		return ProductEntity.builder().name(productDTO.getName()).containArticles(json).build();
	}

	public ProductDTO toDto(ProductEntity productEntity) throws JsonProcessingException {
		ProductArticleDTO[] productArticleDTOS = objectMapper.readValue(productEntity.getContainArticles(), ProductArticleDTO[].class);
		Set<ProductArticleDTO> productArticleDTOSet = new HashSet<ProductArticleDTO>(Arrays.asList(productArticleDTOS));

		return ProductDTO.builder().name(productEntity.getName()).containArticles(productArticleDTOSet).build();
	}
}
