package com.bb.java.developer.controller;

import com.bb.java.developer.ApiValidationException;
import com.bb.java.developer.api.ProductApi;
import com.bb.java.developer.model.ProductRequest;
import com.bb.java.developer.model.ProductResponse;
import com.bb.java.developer.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * TODO - Cache implementation. I have added cache which support to Spring webflux non-blocking code.
 * Couldn't find enough time to test
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Product APIs", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ProductController implements ProductApi {

    private final ProductService productService;

    @Override
    @ApiOperation(value = "Add products to particular the event", response = ProductResponse.class)
    public Mono<ProductResponse> addProduct(@Valid ProductRequest productRequest) {
        log.info("Calling add product service. ProductRequest : {} ", productRequest);
        Validator validators = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ProductRequest>> violations= validators.validate(productRequest);
        if (!violations.isEmpty()) {
            throw new ApiValidationException(violations);
        }
        return productService.addProduct(productRequest);
    }

    @Override
    @ApiOperation(value = "Get all products", response = ProductResponse.class, responseContainer = "List")
    public Flux<ProductResponse> getAllProducts() {
        log.info("Calling get all product service");
        return productService.getAllProducts();
    }

    @Override
    @ApiOperation(value = "Get product by event id", response = ProductResponse.class)
    public Mono<ProductResponse> getProduct(String eventId) {
        log.info("Calling get product service. Event id : {} ", eventId);
        return productService.getProduct(eventId);
    }
}
