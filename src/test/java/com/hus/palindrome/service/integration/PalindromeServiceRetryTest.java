package com.hus.palindrome.service.integration;

import com.hus.palindrome.dto.PalindromeRequest;
import com.hus.palindrome.dto.PalindromeResponse;
import com.hus.palindrome.service.PalindromeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
public class PalindromeServiceRetryTest {
    @Autowired
    private PalindromeService palindromeService;

    @Test
    void whenServiceFails_thenRetryAndFallbackAreTriggered() {
        PalindromeRequest request = new PalindromeRequest("user1", null);
        PalindromeResponse response = palindromeService.processRequest(request);
        assertFalse(response.isPalindrome(), "Fallback should return a non-palindrome response.");
    }
}
