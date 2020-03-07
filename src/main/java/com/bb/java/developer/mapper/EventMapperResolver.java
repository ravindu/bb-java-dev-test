package com.bb.java.developer.mapper;

import com.bb.java.developer.model.Product;
import com.bb.java.developer.model.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapperResolver {

    private final ProductMapper productMapper;

    public Collection<Product> productEntityToDto(Collection<ProductEntity> paymentEntities) {
        return paymentEntities.stream().map(productMapper::entityToDto).collect(Collectors.toList());
    }

    public Collection<ProductEntity> productDtoToEntity(Collection<Product> products) {
        return products.stream().map(productMapper::dtoToEntity).collect(Collectors.toList());
    }
}
