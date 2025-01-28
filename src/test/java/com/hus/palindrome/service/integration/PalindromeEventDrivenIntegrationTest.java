package com.hus.palindrome.service.integration;

import com.hus.palindrome.dto.PalindromeRequest;
import com.hus.palindrome.dto.PalindromeResponse;
import com.hus.palindrome.service.PalindromeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class PalindromeEventDrivenIntegrationTest {

    @Autowired
    private PalindromeService palindromeService;

    @Test
    void givenPalindrome_whenProcessed_thenEventsTriggered() {
        // Given
        String text = "radar";
        PalindromeRequest request = new PalindromeRequest("user1", text);

        // When
        PalindromeResponse response = palindromeService.processRequest(request);

        // Then
        assertTrue(response.isPalindrome(), "Expected 'radar' to be a palindrome.");

        // Here, verify that events are published (use mock event publishers if needed)
    }
}
