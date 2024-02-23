package com.store.products.model.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
	
	private Long idProduct;
	private String description;
	private double price;
	private String category;
	private String title;
	private String brand;
	private int stock;
	private String thumbnail;
	private List<String> gallery;
	private double discountPercentage;
	private double rating;
	 
}
