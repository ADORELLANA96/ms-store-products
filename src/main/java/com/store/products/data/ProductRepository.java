package com.store.products.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.store.products.model.db.Product;

public interface ProductRepository extends ElasticsearchRepository<Product, Long> {

	Optional<Product> findById(Long id);

	Product save(Product product);

	void delete(Product product);

	List<Product> findAll();

}
