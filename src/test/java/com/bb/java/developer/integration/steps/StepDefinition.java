package com.bb.java.developer.integration.steps;

import com.bb.java.developer.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinition extends BaseStep {

    Logger logger = LoggerFactory.getLogger(StepDefinition.class);

    @Given("user want to save products which belong to the particular event {string} {string} {string} {string} {string} {string}")
    public void userSelectsValuesForProductAPI(String eventId, String eventTimeStamp, String productId, String productName, String quantity, String saleAmount) throws Throwable {

        requestBody = new JSONObject();
        if (!eventId.equalsIgnoreCase("NULL")) {
            requestBody.put("id", eventId);
        } else {
            requestBody.put("id", null);
        }

        if (!eventTimeStamp.equalsIgnoreCase("NULL")) {
            requestBody.put("timestamp", eventTimeStamp);
        } else {
            requestBody.put("timestamp", null);
        }

        JSONObject productObj = new JSONObject();
        if (!eventId.equalsIgnoreCase("NULL")) {
            productObj.put("id", Long.parseLong(productId));
        } else {
            productObj.put("id", null);
        }

        if (!eventId.equalsIgnoreCase("NULL")) {
            productObj.put("name", productName);
        } else {
            productObj.put("name", null);
        }

        if (!eventId.equalsIgnoreCase("NULL")) {
            productObj.put("quantity", Integer.parseInt(quantity));
        } else {
            productObj.put("quantity", null);
        }

        if (!eventId.equalsIgnoreCase("NULL")) {
            productObj.put("sale_amount", Double.parseDouble(saleAmount));
        } else {
            productObj.put("sale_amount", null);
        }

        JSONArray productJsonArray = new JSONArray();
        productJsonArray.put(productObj);
        requestBody.put("products", productJsonArray);
        logger.info("Product Request Body" + requestBody.toString());

    }

    @When("POST request trigger to add product request using API {string}")
    public void userDoesPostRequestToProductEndpoint(String path) throws JsonProcessingException {

        request = RestAssured.given().baseUri(baseURI).config(config).body(requestBody.toString());
        request.accept(ContentType.JSON);
        request.contentType(ContentType.JSON);
        response = request.when().post(path);
        if (response.getStatusCode() != HttpStatus.OK.value()) return;
        ProductResponse productResponse = objectMapper.readValue(response.getBody().asString(), ProductResponse.class);
        logger.info("product response received. : {}", productResponse);

    }

    @Then("User receives asynchronous http {int} as a response for add product call")
    public void userReceivesAsynchronousHttpAsAResponse(int httpResponseStatus) throws JsonProcessingException {

        json = response.then().statusCode(httpResponseStatus).contentType(ContentType.JSON);
        if (httpResponseStatus != HttpStatus.OK.value()) return;
        ProductResponse productResponse = objectMapper.readValue(response.getBody().asString(), ProductResponse.class);
        assertNotNull(productResponse, "Product response cannot be null or empty.");
        assertNotNull(productResponse.getId(), "Product response Event Id cannot be null or empty.");
        assertNotNull(productResponse.getTimestamp(), "Event timestamp cannot be null or empty.");
        assertNotNull(productResponse.getProducts(), "Product list cannot be null or empty.");
        assertTrue(productResponse.getProducts().size() > 0, "Product list cannot be null or empty.");
        assertNotNull(productResponse.getProducts().stream().findFirst().get().getId(), "Product id cannot be null.");
        assertNotNull(productResponse.getProducts().stream().findFirst().get().getName(), "Product name cannot be null.");
        assertNotNull(productResponse.getProducts().stream().findFirst().get().getQuantity(), "Product quantity cannot be null.");
        assertNotNull(productResponse.getProducts().stream().findFirst().get().getQuantity(), "Product same amount cannot be null.");
    }

    @Given("User trigger GET request trigger to retrieve products using API {string}")
    public void userRetrieveAllEvents(String path) throws JsonProcessingException {

        request = RestAssured.given().baseUri(baseURI).config(config);
        request.accept(ContentType.JSON);
        request.contentType(ContentType.JSON);
        response = request.when().get(path);
        if (response.getStatusCode() != HttpStatus.OK.value()) return;

        List<ProductResponse> productResponse = response.jsonPath().getList("$");
        logger.info("product response list received. : {}", productResponse);

    }

    @When("User receives asynchronous http {int} as a response for get all product call")
    public void retrieveAllEventResponseCode(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @Given("User want to retrieve products which belong to the particular event {string}")
    public void userWantToFindGivenEventIdDetails(String eventId){
        this.eventId= eventId;
    }

    @When("User trigger GET request trigger to retrieve product based on event id using API {string}")
    public void userRetrieveEventForGivenEventId(String path) throws JsonProcessingException {

        request = RestAssured.given().baseUri(baseURI).config(config);
        request.accept(ContentType.JSON);
        request.contentType(ContentType.JSON);
        String newPath= path+eventId;
        response = request.when().get(newPath);
        if (response.getStatusCode() != HttpStatus.OK.value()) return;

        ProductResponse productResponse = objectMapper.readValue(response.getBody().asString(), ProductResponse.class);
        logger.info("product response list received. : {}", productResponse);

    }

}
