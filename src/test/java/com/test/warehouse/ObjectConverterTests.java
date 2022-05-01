package com.test.warehouse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.warehouse.dto.ArticleDTO;
import com.test.warehouse.entity.ArticleEntity;
import com.test.warehouse.mapper.ObjectConverter;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectConverterTests {

	@Autowired
	ObjectConverter objectConverter = new ObjectConverter();
	@Autowired
	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void checkArticleConversionToDto() throws JsonProcessingException {
		ArticleDTO articleDTO = ArticleDTO.builder().artId("1").name("Test").stock("2").build();

		String articleDTOString = "{\"art_id\": \"1\", \"name\": \"Test\", \"stock\": \"2\" }";

		ArticleDTO articleDTOFromString = objectMapper.readValue(articleDTOString, ArticleDTO.class);

		Assertions.assertEquals(articleDTO.getArtId(), articleDTOFromString.getArtId());
	}

	@Test
	public void checkArticleConversionToEntity() {
		ArticleDTO articleDTO = ArticleDTO.builder().artId("1").name("Test").stock("2").build();

		ArticleEntity articleEntity = ArticleEntity.builder().artId(1).name("Test").stock(2).build();

		ArticleEntity articleEntityFromDto = objectConverter.toEntity(articleDTO);

		Assertions.assertEquals(articleEntity.getArtId(), articleEntityFromDto.getArtId());
	}
}
