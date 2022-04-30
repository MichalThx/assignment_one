//package com.test.warehouse.entity.old;
//
//import org.hibernate.annotations.SQLInsert;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "products")
//@SQLInsert(sql = "INSERT IGNORE INTO products(name, contain_articles) VALUES (?, ?)" )
//public class ProductEntityOld {
//
//	@Id
//	@Column(name="name")
//	String name;
//
////	@Column(columnDefinition = "JSON", name ="contain_articles")
//	String containArticles;
//
//	public ProductEntityOld() {
//	}
//
//	public ProductEntityOld(String name, String containArticles) {
//		this.name = name;
//		this.containArticles = containArticles;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getContainArticles() {
//		return containArticles;
//	}
//
//	public void setContainArticles(String containArticles) {
//		this.containArticles = containArticles;
//	}
//
//	// TODO: is it necessary?
////	@Override
////	public String toString() {
////		return "{" + "name:'" + name + '\'' + ", containArticles:'" + containArticles + '\'' + '}';
////	}
//}
