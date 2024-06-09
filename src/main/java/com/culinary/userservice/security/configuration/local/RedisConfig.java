package com.culinary.userservice.security.configuration.local;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import java.io.IOException;


@Configuration
public class RedisConfig {

    private final RedisServer redisServer;

    public RedisConfig() throws IOException {
        this.redisServer = new RedisServer(6379);
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() throws IOException {
        redisServer.stop();
    }
}