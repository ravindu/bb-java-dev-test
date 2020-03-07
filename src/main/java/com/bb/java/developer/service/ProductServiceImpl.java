package com.bb.java.developer.service;

import com.bb.java.developer.config.WebFluxCache;
import com.bb.java.developer.exception.EventProcessorException;
import com.bb.java.developer.mapper.EventMapper;
import com.bb.java.developer.model.EventEntity;
import com.bb.java.developer.model.ProductRequest;
import com.bb.java.developer.model.ProductResponse;
import com.bb.java.developer.repository.ProductRepository;
import com.bb.java.developer.service.helper.DataAccessHelperService;
import com.bb.java.developer.util.properties.ErrorProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl  implements ProductService{

    private final ProductRepository productRepository;
    private final DataAccessHelperService dataAccessServiceHelper;
    private final EventMapper eventMapper;
    private final ErrorProperties errorProperties;
    //private final WebFluxCache<String, ProductResponse> productResponseCache;

    @Override
    public Mono<ProductResponse> addProduct(ProductRequest productRequest) {

        EventEntity eventEntity = Optional.ofNullable(productRequest).map(eventMapper::dtoToEntity)
                .orElseThrow(() -> new EventProcessorException(errorProperties.getProductRequestMapperError()));
        return dataAccessServiceHelper.asyncCallable(() -> productRepository.save(eventEntity))
                .zipWhen(savedEntity -> Mono.just(eventMapper.entityToDto(savedEntity))).map(Tuple2::getT2);
    }

    @Override
    public Mono<ProductResponse> getProduct(String eventId) {

        return dataAccessServiceHelper.asyncCallable(() -> productRepository.findByEventId(eventId))
                .map(event -> event.map(eventMapper::entityToDto)
                        .orElseThrow(() -> new EventProcessorException(errorProperties.getEventNotFound())));
    }

    @Override
    public Flux<ProductResponse> getAllProducts() {

        return dataAccessServiceHelper.asyncIterable(()->productRepository.findAll().stream().map(eventMapper::entityToDto)
                .collect(Collectors.toList()).iterator());

    }
}
