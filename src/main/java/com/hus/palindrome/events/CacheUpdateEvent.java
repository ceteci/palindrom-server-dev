package com.hus.palindrome.events;

/**
 * Event triggered to update the cache.
 */
public class CacheUpdateEvent {
    private final String key;
    private final boolean value;

    public CacheUpdateEvent(String key, boolean value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public boolean getValue() {
        return value;
    }
}
