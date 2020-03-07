package com.bb.java.developer.controller;

import com.bb.java.developer.model.ProductResponse;
import com.bb.java.developer.service.ProductService;
import com.bb.java.developer.util.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    private ProductController productController;

    @BeforeEach
    public void init(){

        productController= new ProductController(productService);
    }

    @Test
    public void testAddProduct(){

        when(productService.addProduct(any())).thenReturn(Mono.just(TestData.buildProductResponse()));
        Mono<ProductResponse> monoResponse= productController.addProduct(TestData.buildProductRequest());
        ProductResponse productResponse= monoResponse.block();
        assertNotNull(productResponse, "Product response cannot be null.");
        assertNotNull(productResponse.getProducts(),"List of products cannot be null.");
        assertNotNull(productResponse.getTimestamp(), "Event timestamp cannot be null.");
        assertEquals(TestData.EVENT_ID_3, productResponse.getId());
    }

    @Test
    public void testGetProduct(){

        when(productService.getProduct(TestData.EVENT_ID_3)).thenReturn(Mono.just(TestData.buildProductResponse()));
        Mono<ProductResponse> monoResponse= productController.getProduct("EVENT_3");
        ProductResponse productResponse= monoResponse.block();
        assertNotNull(productResponse, "Product response cannot be null.");
        assertNotNull(productResponse.getProducts(),"List of products cannot be null.");
        assertNotNull(productResponse.getTimestamp(), "Event timestamp cannot be null.");
        assertEquals(TestData.EVENT_ID_3, productResponse.getId());
    }

    @Test
    public void testGetAllProducts(){

        when(productService.getAllProducts()).thenReturn(Flux.just(TestData.buildProductResponse()));
        Flux<ProductResponse> fluxResponse= productController.getAllProducts();
        assertNotNull(fluxResponse, "Product response list cannot be null.");
        assertNotNull(fluxResponse.blockFirst().getProducts(), "Product list cannot be null.");
        assertNotNull(fluxResponse.blockFirst().getId(), "Event ids cannot be null.");
        assertNotNull(fluxResponse.blockFirst().getTimestamp(), "Event timestamp cannot be null.");
    }

    //TODO - Negative test cases need to be implemented which cover all negative and exception scenarios.



}
