package com.bb.java.developer.mapper;

import com.bb.java.developer.model.Product;
import com.bb.java.developer.model.ProductEntity;
import com.bb.java.developer.util.TestConstant;
import com.bb.java.developer.util.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventMapperResolverTest {

    @Mock
    private ProductMapper productMapper;

    private EventMapperResolver eventMapperResolver;

    @BeforeEach
    public void init() {
        eventMapperResolver = new EventMapperResolver(productMapper);
    }

    @Test
    public void testProductDtoCollectionToEntityCollection() {

        when(productMapper.dtoToEntity(any())).thenReturn(TestData.buildProductEntity(TestConstant.FIRST_PRODUCT_ID, TestConstant.FIRST_PRODUCT_NAME, TestConstant.FIRST_PRODUCT_QUANTITY, TestConstant.FIRST_PRODUCT_SALE_PRICE));
        Product product_1= TestData.buildProduct(TestConstant.FIRST_PRODUCT_ID, TestConstant.FIRST_PRODUCT_NAME, TestConstant.FIRST_PRODUCT_QUANTITY, TestConstant.FIRST_PRODUCT_SALE_PRICE);
        Collection<Product> productCollection= Arrays.asList(product_1);

        Collection<ProductEntity> productEntityCollection= eventMapperResolver.productDtoToEntity(productCollection);
        assertNotNull(productEntityCollection);

        List<ProductEntity> productEntityList= productEntityCollection.stream().collect(Collectors.toList());
        assertEquals(1, productEntityList.size());
        assertEquals(TestConstant.FIRST_PRODUCT_ID, productEntityList.get(0).getId());
        assertEquals(TestConstant.FIRST_PRODUCT_NAME, productEntityList.get(0).getName());
        assertEquals(TestConstant.FIRST_PRODUCT_QUANTITY, productEntityList.get(0).getQuantity());
        assertEquals(TestConstant.FIRST_PRODUCT_SALE_PRICE, productEntityList.get(0).getSaleAmount());
    }

    @Test
    public void testProductEntityCollectionToDtoCollection() {

        when(productMapper.entityToDto(any())).thenReturn(TestData.buildProduct(TestConstant.SECOND_PRODUCT_ID, TestConstant.SECOND_PRODUCT_NAME, TestConstant.SECOND_PRODUCT_QUANTITY, TestConstant.SECOND_PRODUCT_SALE_PRICE));
        ProductEntity productEntity_1= TestData.buildProductEntity(TestConstant.SECOND_PRODUCT_ID, TestConstant.SECOND_PRODUCT_NAME, TestConstant.SECOND_PRODUCT_QUANTITY, TestConstant.SECOND_PRODUCT_SALE_PRICE);
        Collection<ProductEntity> productEntityCollection= Arrays.asList(productEntity_1);

        Collection<Product> productCollection= eventMapperResolver.productEntityToDto(productEntityCollection);
        assertNotNull(productCollection);

        List<Product> productList= productCollection.stream().collect(Collectors.toList());
        assertEquals(1, productList.size());
        assertEquals(TestConstant.SECOND_PRODUCT_ID, productList.get(0).getId());
        assertEquals(TestConstant.SECOND_PRODUCT_NAME, productList.get(0).getName());
        assertEquals(TestConstant.SECOND_PRODUCT_QUANTITY, productList.get(0).getQuantity());
        assertEquals(TestConstant.SECOND_PRODUCT_SALE_PRICE, productList.get(0).getSale_amount());
    }
}
