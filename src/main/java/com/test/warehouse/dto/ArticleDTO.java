package com.test.warehouse.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleDTO {

	@JsonProperty("art_id")
	private String artId;

	private String name;

	private String stock;
}
