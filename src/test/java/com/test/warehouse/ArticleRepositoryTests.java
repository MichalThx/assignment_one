package com.test.warehouse;

import com.test.warehouse.entity.ArticleEntity;
import com.test.warehouse.repository.ArticleRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleRepositoryTests {

	@Autowired
	private ArticleRepository articleRepository;

	@Test
	public void addArticleToArticleRepository() {
		ArticleEntity articleEntity = ArticleEntity.builder().artId(1).name("Test").stock(100).build();

		articleRepository.saveAndFlush(articleEntity);
		ArticleEntity find = articleRepository.getById(1);
		Assertions.assertEquals(find.getName(), articleEntity.getName());
	}
}
