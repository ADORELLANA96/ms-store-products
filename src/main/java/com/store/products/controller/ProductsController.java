package com.store.products.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.products.model.db.Product;
import com.store.products.model.request.ProductRequest;
import com.store.products.model.response.ProductsQueryResponse;
import com.store.products.service.IProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsController {

	private final IProductService productService;
	
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {

		Boolean removed = productService.removeProduct(productId);

		if (Boolean.TRUE.equals(removed)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping("/products")
	public ResponseEntity<Product> getProduct(@RequestBody ProductRequest request) {

		Product createdProduct = productService.createProduct(request);

		if (createdProduct != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/products")
	public ResponseEntity<ProductsQueryResponse> getProducts(
			@RequestHeader Map<String, String> headers,
			@RequestParam(required = false) String description, 
			@RequestParam(required = false) String category,
			@RequestParam(required = false, defaultValue = "false") Boolean aggregate) {

		log.info("headers: {}", headers);
		ProductsQueryResponse products = productService.getProducts(description, category, aggregate);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Long productId) {

		log.info("Request received for product {}", productId);
		Product product = productService.getProduct(productId);

		if (product != null) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.notFound().build();
		}

	}
	
	@PutMapping("/products")
	public ResponseEntity<Boolean> updateProduct(@RequestBody List<ProductRequest> request) {

		Boolean updated = productService.updateProduct(request);

		if (Boolean.TRUE.equals(updated)) {
			return ResponseEntity.ok(updated);
		} else {
			return ResponseEntity.notFound().build();
		}

	}
	
}
