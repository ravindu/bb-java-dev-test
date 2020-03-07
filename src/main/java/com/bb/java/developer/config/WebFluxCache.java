package com.bb.java.developer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import reactor.cache.CacheMono;

@Component
public class WebFluxCache<K, V> {

    @Autowired
    @Qualifier("redisCache")
    private Cache cache;

    protected Class<K> keyType;
    protected Class<V> valueType;

    public Mono<V> get(Mono<V> retriever, K key) {
        return CacheMono.lookup(monoReader, key).onCacheMissResume(retriever).andWriteWith(monoWriter);
    }

    private Function<K, Mono<Signal<? extends V>>> monoReader =
            k ->
                    Mono.fromCallable(() -> cache.get(k, valueType))
                            .subscribeOn(Schedulers.elastic())
                            .flatMap(t -> Mono.justOrEmpty(Signal.next(t)));

    private BiFunction<K, Signal<? extends V>, Mono<Void>> monoWriter =
            (k, signal) ->
                    Mono.fromRunnable(
                            () ->
                                    Optional.ofNullable(signal.get())
                                            .ifPresent(o -> cache.put(k, o)))
                            .subscribeOn(Schedulers.elastic()).log()
                            .then();
}
