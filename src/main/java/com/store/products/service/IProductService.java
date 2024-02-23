package com.store.products.service;

import java.util.List;

import com.store.products.model.db.Product;
import com.store.products.model.request.ProductRequest;
import com.store.products.model.response.ProductsQueryResponse;

public interface IProductService {

	Product getProduct(Long productId);

	Boolean removeProduct(Long productId);

	ProductsQueryResponse getProducts(String description, String category, Boolean aggregate);

	Product createProduct(ProductRequest request);
	
	Boolean updateProduct(List<ProductRequest> updatedProduct);

}
