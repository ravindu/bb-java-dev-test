package com.bb.java.developer.mapper;

import com.bb.java.developer.model.EventEntity;
import com.bb.java.developer.model.ProductRequest;
import com.bb.java.developer.model.ProductResponse;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {EventMapperResolver.class},
        imports = {java.time.OffsetDateTime.class, java.time.ZoneOffset.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {

    @Mapping( target = "products", source = "productEntities")
    @Mapping( target = "createdDateTime", source = "createdDate")
    @Mapping( target = "updatedDateTime", source = "updatedDate")
    ProductResponse entityToDto(EventEntity eventEntity);

    @Mapping(target = "productEntities", source = "products")
    @Mapping(target="createdDate", expression="java(OffsetDateTime.now(ZoneOffset.ofHours(+8)))")
    @Mapping(target="updatedDate", expression="java(OffsetDateTime.now(ZoneOffset.ofHours(+8)))")
    @Mapping(target = "timestamp", expression="java(OffsetDateTime.now(ZoneOffset.ofHours(+8)))")
    EventEntity dtoToEntity(ProductRequest productRequest);
    @AfterMapping
    default void setEventEntity(@MappingTarget EventEntity eventEntity) {
        eventEntity.addEntityIdToPaymentEntity(eventEntity);
    }

}
