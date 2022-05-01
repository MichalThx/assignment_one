package com.test.warehouse.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "inventory")
public class ArticleEntity {

	@Id
	@Column(name = "art_id")
	private int artId;
	private String name;
	private int stock;
}
