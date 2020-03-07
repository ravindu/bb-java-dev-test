package com.bb.java.developer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class CacheConfiguration {

    private static final String CACHE_NAME = "bbCache";

    @Value("${cache.redis.url}")
    private String redisUrl;

    @Value("${cache.redis.port}")
    private int redisPort;

    @Bean(name="redisCacheManager")
    public CacheManager redisCacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(RedisSerializer.json()));
        redisCacheConfiguration.usePrefix();

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(
                getRedisConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean(name = "redisCache")
    public Cache redisCache() {
        return redisCacheManager().getCache(CACHE_NAME);
    }

    @Bean
    public LettuceConnectionFactory getRedisConnectionFactory() {
        return new LettuceConnectionFactory(redisUrl, redisPort);
    }

}
