package com.bb.java.developer.service;

import com.bb.java.developer.model.ProductRequest;
import com.bb.java.developer.model.ProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ProductResponse> addProduct(ProductRequest productRequest);

    Mono<ProductResponse> getProduct(String eventId);

    Flux<ProductResponse> getAllProducts();
}
