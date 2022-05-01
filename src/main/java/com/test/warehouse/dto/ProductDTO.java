package com.test.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {

	private String name;

	@JsonProperty("contain_articles")
	private Set<ProductArticleDTO> containArticles = new HashSet<>();

}
