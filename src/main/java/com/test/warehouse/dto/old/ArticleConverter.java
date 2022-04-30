//package com.test.warehouse.dto.old;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.test.warehouse.dto.old.Article;
//
//import javax.persistence.AttributeConverter;
//import java.io.IOException;
//import java.util.Map;
//
//public class ArticleConverter implements AttributeConverter<Map<String, Article>, String> {
//	private static ObjectMapper objectMapper;
//
//
//	static {
//		objectMapper = new ObjectMapper();
//	}
//	@Override
//	public String convertToDatabaseColumn(Map<String, Article> stringArticleMap) {
//		String stringArticleJson = null;
//		try {
//			stringArticleJson = objectMapper.writeValueAsString(stringArticleMap);
//		} catch (final JsonProcessingException e) {}
//
//		return stringArticleJson;
//	}
//
//	@Override
//	public Map<String, Article> convertToEntityAttribute(String s) {
//		Map<String, Article> articlesMap = null;
//		try {
//			articlesMap = objectMapper.readValue(s, Map.class);
//		} catch (final IOException e) {	}
//
//		return articlesMap;
//	}
//}
