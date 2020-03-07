package com.bb.java.developer.service.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DataAccessHelperServiceTest {

    private DataAccessHelperService dataAccessServiceHelper;

    @BeforeEach
    public void beforeTest() {
        dataAccessServiceHelper = new DataAccessHelperService(Schedulers.fromExecutor(Executors.newFixedThreadPool(1)));
    }

    @Test
    public void asyncCallableTest() {
        String inputString = "testcomplete";
        Mono<String> mono = dataAccessServiceHelper.asyncCallable(() -> inputString);
        String result = mono.block();
        assertNotNull(result);
        assertEquals(inputString,result);
    }

    @Test
    public void asyncIterableTest() {
        Flux<String> flux = dataAccessServiceHelper.asyncIterable(()-> Arrays.asList("s1","s2").iterator());
        String firstString = flux.blockFirst();
        String secondString = flux.blockLast();
        assertNotNull(firstString);
        assertNotNull(secondString);
        assertEquals("s1",firstString);
        assertEquals("s2",secondString);
    }

}
