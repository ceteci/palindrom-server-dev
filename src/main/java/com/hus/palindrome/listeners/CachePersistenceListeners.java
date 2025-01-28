package com.hus.palindrome.listeners;

import com.hus.palindrome.cache.PalindromeCache;
import com.hus.palindrome.events.CacheUpdateEvent;
import com.hus.palindrome.events.PersistenceSaveEvent;
import com.hus.palindrome.persistance.PersistenceManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listens to cache and persistence-related events and performs the necessary actions.
 */
@Component
public class CachePersistenceListeners {

    private final PalindromeCache cache;
    private final PersistenceManager persistenceManager;

    public CachePersistenceListeners(PalindromeCache cache, PersistenceManager persistenceManager) {
        this.cache = cache;
        this.persistenceManager = persistenceManager;
    }

    /**
     * Handles cache updates.
     */
    @EventListener
    public void handleCacheUpdate(CacheUpdateEvent event) {
        cache.put(event.getKey(), event.getValue());
    }

    /**
     * Handles persistence saves.
     */
    @EventListener
    public void handlePersistenceSave(PersistenceSaveEvent event) {
        persistenceManager.save(event.getValue());
    }
}
