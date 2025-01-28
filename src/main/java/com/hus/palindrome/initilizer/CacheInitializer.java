package com.hus.palindrome.initilizer;

import com.hus.palindrome.cache.PalindromeCache;
import com.hus.palindrome.persistance.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CacheInitializer {

    private static final Logger logger = LoggerFactory.getLogger(CacheInitializer.class);

    private final PalindromeCache cache;
    private final PersistenceManager persistenceManager;

    public CacheInitializer(PalindromeCache cache, PersistenceManager persistenceManager) {
        this.cache = cache;
        this.persistenceManager = persistenceManager;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void initializeCache() {
        logger.info("Initializing cache from persistence layer...");
        persistenceManager.load().forEach(value -> cache.put(value, true));
        logger.info("Cache initialization complete with {} entries.", cache.size());
    }
}
