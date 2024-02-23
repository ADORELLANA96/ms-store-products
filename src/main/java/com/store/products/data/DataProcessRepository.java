package com.store.products.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import com.store.products.model.db.Product;
import com.store.products.model.response.AggregationDetails;
import com.store.products.model.response.ProductsQueryResponse;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DataProcessRepository {

	private final ProductRepository productRepository;
	private final ElasticsearchOperations elasticClient;

	private final String[] descriptionSearchFields = { "description", "description._2gram", "description._3gram",
			"title", "brand" };

	public Product save(Product product) {
		return productRepository.save(product);
	}

	public Boolean delete(Product product) {
		productRepository.delete(product);
		return Boolean.TRUE;
	}

	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	@SneakyThrows
	public ProductsQueryResponse findProducts(String description, String category, Boolean aggregate) {

		BoolQueryBuilder querySpec = QueryBuilders.boolQuery();

		if (!StringUtils.isEmpty(category)) {
			querySpec.must(QueryBuilders.termQuery("category", category));
		}

		if (!StringUtils.isEmpty(description)) {
			querySpec.must(QueryBuilders.multiMatchQuery(description, descriptionSearchFields).type(Type.BOOL_PREFIX)
					.operator(Operator.OR));
		}

		if (!querySpec.hasClauses()) {
			querySpec.must(QueryBuilders.matchAllQuery());
		}

		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(querySpec);

		if (aggregate) {
			nativeSearchQueryBuilder
					.addAggregation(AggregationBuilders.terms("Category Aggregation").field("category").size(1000));
			nativeSearchQueryBuilder.withMaxResults(0);
		}
		Query query = nativeSearchQueryBuilder.build();
		SearchHits<Product> result = elasticClient.search(query, Product.class);

		List<AggregationDetails> responseAggs = new LinkedList<>();

		if (result.hasAggregations()) {
			Map<String, Aggregation> aggs = result.getAggregations().asMap();
			ParsedStringTerms countryAgg = (ParsedStringTerms) aggs.get("Category Aggregation");
			countryAgg.getBuckets().forEach(bucket -> responseAggs
					.add(new AggregationDetails(bucket.getKey().toString(), (int) bucket.getDocCount())));
		}

		log.info("responseAggs-> {}", responseAggs);

		return new ProductsQueryResponse(result.getSearchHits().stream().map(SearchHit::getContent).toList(),
				responseAggs);
	}

	public Boolean updateProduct(List<Product> updatedProduct) {
		Boolean updated = false;
		for (Product itemProduct : updatedProduct) {
			Product existingProduct = productRepository.findById(itemProduct.getIdProduct()).orElse(null);
			//actualizacmos solo stock en este caso
			if (existingProduct != null) {
				existingProduct.setStock(existingProduct.getStock() - itemProduct.getStock());
				productRepository.save(existingProduct);
				updated = true;
			} else
				updated = false;
		}
		return updated;
	}

}
