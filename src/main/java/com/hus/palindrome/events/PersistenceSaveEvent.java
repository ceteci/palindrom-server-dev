package com.hus.palindrome.events;

/**
 * Event triggered to save a new value in the persistence layer.
 */
public class PersistenceSaveEvent {
    private final String value;

    public PersistenceSaveEvent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

