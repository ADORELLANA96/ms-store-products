package com.store.products.model.db;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(indexName = "products", createIndex= true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {
		
	@Id
	 private Long idProduct;
	 
	@Field(type=FieldType.Search_As_You_Type, name = "title")
	 private String title;
	
	@Field(type = FieldType.Search_As_You_Type, name = "description")
	 private String description;
	
	@Field(type=FieldType.Double, name = "price")
	 private Double price;
	
	@Field(type=FieldType.Double, name = "discountPercentage")
	 private Double discountPercentage;
	
	@Field(type=FieldType.Double, name = "rating")
	 private Double rating;
	
	@Field(type=FieldType.Integer, name = "stock")
	 private int stock;
	
	@Field(type=FieldType.Search_As_You_Type, name = "brand")
	 private String brand;
	
	@Field(type = FieldType.Keyword, name = "category")
	 private String category;
	
	@Field(type=FieldType.Keyword, name = "thumbnail")
	private String thumbnail;
	
	 @Field(type = FieldType.Text)
	 private List<String> gallery;
	 
}
