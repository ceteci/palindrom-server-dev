package com.hus.palindrome.service.integration;

import com.hus.palindrome.config.PalindromeProperties;
import com.hus.palindrome.config.PersistenceFactorySelector;
import com.hus.palindrome.dto.PalindromeRequest;
import com.hus.palindrome.dto.PalindromeResponse;
import com.hus.palindrome.persistance.factory.FilePersistenceFactory;
import com.hus.palindrome.persistance.factory.PersistenceFactory;
import com.hus.palindrome.service.PalindromeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Validates the interaction between the PalindromeService and the file.
 */
@SpringBootTest
@ActiveProfiles("test")
public class PalindromeServiceFileIntegrationTest {
    @Autowired
    private PalindromeService palindromeService;

    @Autowired
    private PersistenceFactorySelector factorySelector;

    @Autowired
    private PalindromeProperties properties;

    @Test
    void givenPalindrome_whenProcessed_thenPersistedToDatabase() {
        // Given
        properties.getPersistence().setMode("file");
        PersistenceFactory factory = factorySelector.select();
        assertInstanceOf(FilePersistenceFactory.class, factory, "Expected FilePersistenceFactory to be selected.");

        String text = "radar";
        PalindromeRequest request = new PalindromeRequest("user1", text);

        // When
        PalindromeResponse response = palindromeService.processRequest(request);

        // Then
        assertTrue(response.isPalindrome(), "Expected 'radar' to be a palindrome.");
    }
}

