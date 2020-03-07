package com.bb.java.developer.mapper;

import com.bb.java.developer.model.Product;
import com.bb.java.developer.model.ProductEntity;
import com.bb.java.developer.util.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProductMapperTest {

    @InjectMocks
    private ProductMapperImpl productMapper;

    @Test
    public void testDtoToEntity(){

        Product product= TestData.buildProduct(22335678912345L, "Product_15", 2, 93.48);

        ProductEntity productEntity= productMapper.dtoToEntity(product);
        assertNotNull(productEntity);
        assertNotNull(productEntity.getCreatedDate());
        assertNotNull(productEntity.getUpdatedDate());
        assertEquals(22335678912345L, productEntity.getId());
        assertEquals("Product_15", productEntity.getName());
        assertEquals(2, productEntity.getQuantity());
        assertEquals(93.48, productEntity.getSaleAmount());


    }
}
