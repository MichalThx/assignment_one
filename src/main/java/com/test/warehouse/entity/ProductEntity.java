package com.test.warehouse.entity;

import lombok.*;
import org.hibernate.annotations.SQLInsert;

import javax.persistence.*;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class ProductEntity {

	@Id
	private String name;

	@Column(name = "contain_articles")
	private String containArticles;

}
