package com.bb.java.developer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.WebFilter;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
class BeanConfig {

    @Value("${spring.datasource.maximum-pool-size:10}")
    private int connectionPoolSize;

    @Autowired
    private ServerProperties serverProperties;

    @Bean
    public Scheduler jdbcScheduler() {
        return Schedulers.fromExecutor(new ThreadPoolExecutor(0, connectionPoolSize,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>()));
    }

    @Bean
    public WebFilter contextPathWebFilter() {
        String contextPath = serverProperties.getServlet().getContextPath();
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (request.getURI().getPath().startsWith(contextPath)) {
                return chain.filter(
                        exchange.mutate()
                                .request(request.mutate().contextPath(contextPath).build())
                                .build());
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        };
    }
}