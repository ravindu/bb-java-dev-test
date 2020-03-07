package com.bb.java.developer.mapper;

import com.bb.java.developer.model.*;
import com.bb.java.developer.util.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventMapperTest {

    @Mock
    EventMapperResolver eventMapperResolver;

    private EventMapper eventMapper;

    @BeforeEach
    public void init(){
        eventMapper = new EventMapperImpl(eventMapperResolver);
    }

    @Test
    public void testDtoToEntity(){

        ProductEntity productEntity= TestData.buildProductEntity(12345678912345L, "Product_1", 2, 45.78);
        when(eventMapperResolver.productDtoToEntity(anyList())).thenReturn(Arrays.asList(productEntity));

        ProductRequest request= TestData.buildProductRequest();
        EventEntity eventEntity= eventMapper.dtoToEntity(request);
        assertNotNull(eventEntity);
        assertNotNull(eventEntity.getProductEntities());
        assertEquals("EVENT_3", eventEntity.getId());
        assertEquals(12345678912345L, eventEntity.getProductEntities().stream().findFirst().get().getId());
        assertEquals("Product_1", eventEntity.getProductEntities().stream().findFirst().get().getName());
        assertEquals(2, eventEntity.getProductEntities().stream().findFirst().get().getQuantity());
        assertEquals(45.78, eventEntity.getProductEntities().stream().findFirst().get().getSaleAmount());
    }

    @Test
    public void testEntityToDto(){

        when(eventMapperResolver.productEntityToDto(anyList())).thenReturn(TestData.buildListOfProducts());

        EventEntity eventEntity= TestData.buildEventEntity();
        ProductResponse response= eventMapper.entityToDto(eventEntity);
        assertNotNull(response);
        assertNotNull(response.getTimestamp());
        assertNotNull(response.getProducts());

        List<Product> productList= response.getProducts().stream().collect(Collectors.toList());
        assertEquals(3, productList.size());
        assertEquals("EVT_1", response.getId());
        assertEquals(3, response.getProducts().size());
        assertEquals(12345678898L, productList.get(1).getId());
        assertEquals("PRODUCT_3", productList.get(2).getName());
        assertEquals(1, productList.get(0).getQuantity());
        assertEquals(7.45, productList.get(2).getSale_amount());


    }

}
