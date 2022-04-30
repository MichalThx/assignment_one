package com.test.warehouse.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductArticleDTO {

	@JsonProperty("art_id")
	private String artId;

	@JsonProperty("amount_of")
	private String amountOf;
}
