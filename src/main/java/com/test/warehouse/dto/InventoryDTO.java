package com.test.warehouse.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InventoryDTO {

	private Set<ArticleDTO> inventory = new HashSet<>();
}
