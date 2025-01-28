package com.hus.palindrome.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class PalindromeConfigurationTest {

    @Autowired
    private PalindromeProperties properties;

    @Test
    void testPersistenceMode() {
        assertEquals("file", properties.getPersistence().getMode(), "Persistence mode should be 'file' in the test profile.");
    }
}