package com.bb.java.developer.util.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "product.request.validation.message")
public class ErrorProperties {

    private String id;
    private String name;
    private String quantity;
    private String minSaleAmount;
    private String minSaleAmountRange;
    private String productRequestId;
    private String productList;
    private String productRequestMapperError;
    private String eventNotFound;
    private String internalServerError;
}
