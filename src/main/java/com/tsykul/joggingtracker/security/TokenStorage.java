package com.tsykul.joggingtracker.security;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author KonstantinTsykulenko
 * @since 7/20/2014.
 */
@Service
public class TokenStorage {
    private final Cache<String, String> tokens = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    public void putToken(String username, String token) {
        tokens.put(username, token);
    }

    public String getToken(String username) {
        return tokens.getIfPresent(username);
    }

    public void removeToken(String username) {
        tokens.invalidate(username);
    }
}
