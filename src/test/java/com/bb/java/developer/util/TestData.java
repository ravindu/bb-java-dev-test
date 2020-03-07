package com.bb.java.developer.util;


import com.bb.java.developer.model.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class TestData implements TestConstant {

    public static Product buildProduct(Long id, String productName, Integer quantity, Double price){
        Product product= new Product();
        product.setId(id);
        product.setName(productName);
        product.setQuantity(quantity);
        product.setSale_amount(price);
        return product;
    }

    public static ProductEntity buildProductEntity(Long productId, String name, Integer quantity, Double price){

        ProductEntity productEntity= new ProductEntity();
        productEntity.setId(productId);
        productEntity.setName(name);
        productEntity.setQuantity(quantity);
        productEntity.setSaleAmount(price);
        productEntity.setCreatedDate(OffsetDateTime.now(ZoneOffset.ofHours(+8)));
        productEntity.setUpdatedDate(OffsetDateTime.now(ZoneOffset.ofHours(+8)));
        return productEntity;
    }

    public static EventEntity buildEventEntity() {
        EventEntity eventEntity= new EventEntity();
        eventEntity.setId(TestConstant.EVENT_ID_1);
        eventEntity.setTimestamp(OffsetDateTime.now(ZoneOffset.ofHours(+8)));
        eventEntity.setCreatedDate(OffsetDateTime.now(ZoneOffset.ofHours(+8)));
        eventEntity.setUpdatedDate(OffsetDateTime.now(ZoneOffset.ofHours(+8)));
        eventEntity.setProductEntities(buildListOfProductEntities());
        return eventEntity;

    }

   public static List<Product> buildListOfProducts(){

        List<Product> productList= new ArrayList<>();
        productList.add(buildProduct(TestConstant.FIRST_PRODUCT_ID, TestConstant.FIRST_PRODUCT_NAME, TestConstant.FIRST_PRODUCT_QUANTITY, 3.45));
        productList.add(buildProduct(TestConstant.SECOND_PRODUCT_ID, TestConstant.SECOND_PRODUCT_NAME, TestConstant.SECOND_PRODUCT_QUANTITY, 5.45));
        productList.add(buildProduct(TestConstant.THIRD_PRODUCT_ID, TestConstant.THIRD_PRODUCT_NAME,  TestConstant.SECOND_PRODUCT_QUANTITY, 7.45));
        return productList;
    }

    public static List<ProductEntity> buildListOfProductEntities(){

        List<ProductEntity> productEntityList= new ArrayList<>();
        productEntityList.add(buildProductEntity(TestConstant.FIRST_PRODUCT_ID,TestConstant.FIRST_PRODUCT_NAME, TestConstant.FIRST_PRODUCT_QUANTITY, TestConstant.FIRST_PRODUCT_SALE_PRICE ));
        productEntityList.add(buildProductEntity(TestConstant.SECOND_PRODUCT_ID,TestConstant.SECOND_PRODUCT_NAME, TestConstant.SECOND_PRODUCT_QUANTITY, TestConstant.SECOND_PRODUCT_SALE_PRICE ));
        productEntityList.add(buildProductEntity(TestConstant.THIRD_PRODUCT_ID,TestConstant.THIRD_PRODUCT_NAME, TestConstant.THIRD_PRODUCT_QUANTITY, TestConstant.THIRD_PRODUCT_SALE_PRICE ));
        return productEntityList;
    }

    public static ProductResponse buildProductResponse(){

        ProductResponse productResponse= new ProductResponse();
        productResponse.setId(TestConstant.EVENT_ID_3);
        productResponse.setProducts(buildListOfProducts());
        productResponse.setTimestamp(OffsetDateTime.now(ZoneOffset.ofHours(+8)));
        return productResponse;
    }

    public static ProductRequest buildProductRequest(){

        ProductRequest productRequest= new ProductRequest();
        productRequest.setId(TestConstant.EVENT_ID_3);
        productRequest.setTimestamp(TestConstant.EVENT_TIMESTAMP);
        productRequest.setProducts(buildListOfProducts());
        return productRequest;
    }

}
