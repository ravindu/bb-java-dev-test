package com.bb.java.developer.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Callable;

@Component
@RequiredArgsConstructor
public class DataAccessHelperService {

    private final Scheduler jdbcScheduler;

    public <S> Mono<S> asyncCallable(Callable<S> callable) {
        return Mono.fromCallable(callable).subscribeOn(Schedulers.parallel()).publishOn(jdbcScheduler);
    }

    public <S> Flux<S> asyncIterable(Iterable<S> iterable) {
        return Flux.fromIterable(iterable).subscribeOn(Schedulers.parallel()).publishOn(jdbcScheduler);
    }
}
