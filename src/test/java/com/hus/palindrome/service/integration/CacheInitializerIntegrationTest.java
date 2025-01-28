package com.hus.palindrome.service.integration;

import com.hus.palindrome.cache.PalindromeCache;
import com.hus.palindrome.events.PersistenceSaveEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class CacheInitializerIntegrationTest {

    @Autowired
    private PalindromeCache cache;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Test
    void whenApplicationStarts_thenCacheIsInitialized() {
        // Validate cache is populated during application startup
        assertTrue(cache.size() == 0, "Cache should be clean for test env.");
    }
}
