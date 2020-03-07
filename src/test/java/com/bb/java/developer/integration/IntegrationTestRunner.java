package com.bb.java.developer.integration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = { "src/test/resources/feature" },
        glue = { "com.bb.java.developer.integration.steps" },
        plugin = { "pretty", "html:build/cucumber/report", "json:build/cucumber/json" },
        tags = { "not @Ignore" }
)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTestRunner {

}
