package com.bb.java.developer.mapper;

import com.bb.java.developer.model.*;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        imports = {java.time.OffsetDateTime.class, java.time.ZoneOffset.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {

    @Mapping(target="createdDate", expression="java(OffsetDateTime.now(ZoneOffset.ofHours(+8)))")
    @Mapping(target="updatedDate", expression="java(OffsetDateTime.now(ZoneOffset.ofHours(+8)))")
    @Mapping(target = "saleAmount", source = "sale_amount")
    @Mapping(target = "eventEntity", ignore = true)
    ProductEntity dtoToEntity(Product product);

    @Mapping(target = "sale_amount", source = "saleAmount")
    Product entityToDto(ProductEntity productEntity);
}
