package com.hus.palindrome.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SanitizationUtilsTest {

    @Test
    void givenArgsWithPassword_whenSanitized_thenMasksPassword() {
        // Given
        Object[] inputArgs = {"user1", "password123", "securePassword"};

        // When
        Object[] sanitizedArgs = SanitizationUtils.sanitizeArgs(inputArgs);

        // Then
        assertArrayEquals(new Object[]{"user1", "****", "****"}, sanitizedArgs, "Password arguments should be masked.");
    }

    @Test
    void givenArgsWithoutPassword_whenSanitized_thenUnchanged() {
        // Given
        Object[] inputArgs = {"user1", "token123", "example"};

        // When
        Object[] sanitizedArgs = SanitizationUtils.sanitizeArgs(inputArgs);

        // Then
        assertArrayEquals(inputArgs, sanitizedArgs, "Arguments without sensitive data should remain unchanged.");
    }

    @Test
    void givenMixedArgs_whenSanitized_thenMasksOnlySensitiveData() {
        // Given
        Object[] inputArgs = {"user1", "password123", 42, true, "securePassword"};

        // When
        Object[] sanitizedArgs = SanitizationUtils.sanitizeArgs(inputArgs);

        // Then
        assertArrayEquals(new Object[]{"user1", "****", 42, true, "****"}, sanitizedArgs, "Only sensitive arguments should be masked.");
    }

    @Test
    void givenNullArgs_whenSanitized_thenReturnsEmptyArray() {
        // Given
        Object[] inputArgs = null;

        // When
        Object[] sanitizedArgs = SanitizationUtils.sanitizeArgs(inputArgs);

        // Then
        assertArrayEquals(new Object[]{}, sanitizedArgs, "Null input should return an empty array.");
    }

    @Test
    void givenEmptyArgs_whenSanitized_thenReturnsEmptyArray() {
        // Given
        Object[] inputArgs = {};

        // When
        Object[] sanitizedArgs = SanitizationUtils.sanitizeArgs(inputArgs);

        // Then
        assertArrayEquals(inputArgs, sanitizedArgs, "Empty array should remain unchanged.");
    }
}