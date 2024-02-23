package com.store.products.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.store.products.data.DataProcessRepository;
import com.store.products.model.db.Product;
import com.store.products.model.request.ProductRequest;
import com.store.products.model.response.ProductsQueryResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService{
	
	private final DataProcessRepository repository;
	
	@Override
	public ProductsQueryResponse getProducts(String detail, String category, Boolean aggregate) {
		return repository.findProducts(detail, category, aggregate);
	}
	
	@Override
	public Product getProduct(Long productId) {
		return repository.findById(productId).orElse(null);
	}

	@Override
	public Boolean removeProduct(Long productId) {

		Product product = repository.findById(productId).orElse(null);
		if (product != null) {
			repository.delete(product);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
	@Override
	public Product createProduct(ProductRequest request) {

		if (request != null && request.getCategory() != null
				&& request.getDescription() != null
				&& request.getPrice() > 0) {

			Product product = Product.builder()
					.idProduct(request.getIdProduct())
					.description(request.getDescription())
					.title(request.getTitle())
					.category(request.getCategory())
					.brand(request.getBrand())
					.price(request.getPrice())
					.discountPercentage(request.getDiscountPercentage())
					.rating(request.getRating())
					.gallery(request.getGallery())
					.stock(request.getStock())
					.thumbnail(request.getThumbnail())
					.build();

			return repository.save(product);
		} else {
			return null;
		}
	}

	@Override
	public Boolean updateProduct(List<ProductRequest> updatedProduct) {
		List<Product> lstProduct = new ArrayList<>();
		for (ProductRequest request : updatedProduct) {
			Product product = Product.builder()
					.idProduct(request.getIdProduct())
					.description(request.getDescription())
					.title(request.getTitle())
					.category(request.getCategory())
					.brand(request.getBrand())
					.price(request.getPrice())
					.discountPercentage(request.getDiscountPercentage())
					.rating(request.getRating())
					.gallery(request.getGallery())
					.stock(request.getStock())
					.thumbnail(request.getThumbnail())
					.build();
			lstProduct.add(product);
		}
		return repository.updateProduct(lstProduct);
	}

}
