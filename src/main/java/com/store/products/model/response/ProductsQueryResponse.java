package com.store.products.model.response;

import java.util.List;

import com.store.products.model.db.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsQueryResponse {
	
	private List<Product> products;
	 private List<AggregationDetails> aggs;
	
}
