package com.bb.java.developer.service;

import com.bb.java.developer.mapper.EventMapper;
import com.bb.java.developer.mapper.EventMapperImpl;
import com.bb.java.developer.mapper.EventMapperResolver;
import com.bb.java.developer.model.Product;
import com.bb.java.developer.model.ProductResponse;
import com.bb.java.developer.repository.ProductRepository;
import com.bb.java.developer.service.helper.DataAccessHelperService;
import com.bb.java.developer.util.TestConstant;
import com.bb.java.developer.util.TestData;
import com.bb.java.developer.util.properties.ErrorProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    EventMapperResolver eventMapperResolver;

    @Mock
    private DataAccessHelperService dataAccessServiceHelper;

    @Mock
    private ErrorProperties errorProperties;

    private EventMapper eventMapper;

    private ProductServiceImpl productService;

    @BeforeEach
    public void init(){
        eventMapper = new EventMapperImpl(eventMapperResolver);
        productService = new ProductServiceImpl(productRepository, dataAccessServiceHelper, eventMapper, errorProperties);

    }

    @Test
    public void addProducts() {

        when(eventMapperResolver.productDtoToEntity(anyCollection())).thenReturn(TestData.buildListOfProductEntities());
        when(eventMapperResolver.productEntityToDto(anyCollection())).thenReturn(TestData.buildListOfProducts());
        when(dataAccessServiceHelper.asyncCallable(any())).thenReturn(Mono.just(TestData.buildEventEntity()));
        Mono<ProductResponse> monoProductResponse = productService.addProduct(TestData.buildProductRequest());
        assertNotNull(monoProductResponse, "Product mono response cannot be null.");

        assertNotNull(monoProductResponse.block(), "Product response cannot be null.");
        assertEquals("Event id should be matched.", TestConstant.EVENT_ID_1, monoProductResponse.block().getId());

        List<Product> productList= monoProductResponse.block().getProducts().stream().collect(Collectors.toList());
        assertNotNull(productList, "Product  list cannot be null.");
        assertEquals("Product list cannot be empty", 3, productList.size());
        assertEquals("Product name should be matched.", TestData.FIRST_PRODUCT_NAME, productList.get(0).getName());
        assertEquals("Product quantity should be matched.",3, productList.get(1).getQuantity());
        assertTrue(7.45== productList.get(2).getSale_amount(), "Product sale amount should be matched.");
    }

    @Test
    public void getProducts(){

        when(eventMapperResolver.productEntityToDto(anyCollection())).thenReturn(Arrays.asList(TestData.buildProduct(12345678898L,"PRODUCT_2", 3, 5.45)));
        when(dataAccessServiceHelper.asyncCallable(any())).thenReturn(Mono.just(Optional.of(TestData.buildEventEntity())));
        Mono<ProductResponse> monoProductResponse = productService.getProduct(TestConstant.EVENT_ID_1);

        assertNotNull(monoProductResponse, "Product mono object cannot be null or empty.");
        assertNotNull(monoProductResponse.block(), "Product response cannot be null.");
        assertEquals("Invalid event id founds.", TestData.EVENT_ID_1, monoProductResponse.block().getId());

        List<Product> productList= monoProductResponse.block().getProducts().stream().collect(Collectors.toList());
        assertNotNull(productList, "Product list cannot be null.");
        assertEquals("Product list cannot be empty.", 1, productList.size());
        assertEquals("Product name should be matched.", TestData.SECOND_PRODUCT_NAME, productList.get(0).getName());
        assertEquals("Product quantity should be matched.", 3, productList.get(0).getQuantity());
        assertTrue(5.45==productList.get(0).getSale_amount(), "Product sale amount should be matched.");

    }

    @Test
    public void getAllProducts(){

        Flux<Object> eventEntities = Flux.fromIterable(Collections.singletonList(TestData.buildEventEntity()));
        when(dataAccessServiceHelper.asyncIterable(any())).thenReturn(eventEntities);
        Flux<ProductResponse> fluxResponse= productService.getAllProducts();

        assertNotNull(fluxResponse, "Product mono object cannot be null or empty.");
        assertNotNull(fluxResponse.blockFirst(), "First product response cannot be null.");
        assertNotNull(fluxResponse.blockLast(), "Last product response cannot be null.");

    }

    //TODO - Negative test cases need to be implemented which cover all negative and exception scenarios.

}
