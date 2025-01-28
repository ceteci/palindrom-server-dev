package com.hus.palindrome.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class PalindromeCache {

    private static final Logger logger = LoggerFactory.getLogger(PalindromeCache.class);
    private final ConcurrentHashMap<String, Boolean> cache = new ConcurrentHashMap<>();

    public Boolean get(String key) {
        return cache.get(key);
    }

    public void put(String key, Boolean value) {
        logger.info("Adding '{}' to cache with value: {}", key, value);
        cache.put(key, value);
    }

    public int size() {
        return cache.size();
    }
}
