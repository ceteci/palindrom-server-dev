package com.hus.palindrome.service.unit;

import com.hus.palindrome.cache.PalindromeCache;
import com.hus.palindrome.dto.PalindromeRequest;
import com.hus.palindrome.dto.PalindromeResponse;
import com.hus.palindrome.events.CacheUpdateEvent;
import com.hus.palindrome.events.PersistenceSaveEvent;
import com.hus.palindrome.service.PalindromeService;
import com.hus.palindrome.validation.chain.ValidationChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentCaptor.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PalindromeServiceTest {

    private PalindromeService palindromeService;

    @Mock
    private ValidationChain mockValidationChain;

    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private ApplicationEventPublisher mockEventPublisher;

    @Mock
    private PalindromeCache mockCache;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        palindromeService = new PalindromeService(mockValidationChain, mockModelMapper, mockEventPublisher, mockCache);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void givenValidPalindrome_whenProcessed_thenPublishesCacheAndPersistenceEvents() {
        // Given
        String text = "radar";
        PalindromeRequest request = new PalindromeRequest("user1", text);
        PalindromeResponse expectedResponse = new PalindromeResponse(true, text);

        when(mockModelMapper.map(request, PalindromeResponse.class)).thenReturn(expectedResponse);
        when(mockCache.get(text)).thenReturn(null);

        // When
        palindromeService.processRequest(request);

        // Capture events
        ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);
        verify(mockEventPublisher, times(2)).publishEvent(eventCaptor.capture());

        // Verify events
        assertTrue(eventCaptor.getAllValues().stream()
                        .anyMatch(event -> event instanceof CacheUpdateEvent && ((CacheUpdateEvent) event).getKey().equals("radar")),
                "Expected CacheUpdateEvent for 'radar'.");

        assertTrue(eventCaptor.getAllValues().stream()
                        .anyMatch(event -> event instanceof PersistenceSaveEvent && ((PersistenceSaveEvent) event).getValue().equals("radar")),
                "Expected PersistenceSaveEvent for 'radar'.");
    }

    @Test
    void givenNonPalindrome_whenProcessed_thenNoPersistenceEventPublished() {
        // Given
        String text = "java";
        PalindromeRequest request = new PalindromeRequest("user1", text);
        PalindromeResponse expectedResponse = new PalindromeResponse(false, text);

        when(mockModelMapper.map(request, PalindromeResponse.class)).thenReturn(expectedResponse);
        when(mockCache.get(text)).thenReturn(null);

        // When
        PalindromeResponse response = palindromeService.processRequest(request);

        // Then
        assertFalse(response.isPalindrome());
        verify(mockEventPublisher, never()).publishEvent(any(PersistenceSaveEvent.class));
    }

    @Test
    void givenCachedPalindrome_whenProcessed_thenNoEventPublished() {
        // Given
        String text = "radar";
        PalindromeRequest request = new PalindromeRequest("user1", text);
        PalindromeResponse expectedResponse = new PalindromeResponse(true, text);

        when(mockModelMapper.map(request, PalindromeResponse.class)).thenReturn(expectedResponse);
        when(mockCache.get(text)).thenReturn(true);

        // When
        PalindromeResponse response = palindromeService.processRequest(request);

        // Then
        assertTrue(response.isPalindrome());
        verify(mockEventPublisher, never()).publishEvent(any());
    }

    @Test
    void givenNullInput_whenProcessed_thenThrowsIllegalArgumentException() {
        // Given
        PalindromeRequest request = new PalindromeRequest("user1", null);

        doThrow(new IllegalArgumentException("Text cannot be null or empty"))
                .when(mockValidationChain).validate(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> palindromeService.processRequest(request));

        assertEquals("Text cannot be null or empty", exception.getMessage());
    }

    @Test
    void givenEmptyInput_whenProcessed_thenThrowsIllegalArgumentException() {
        // Given
        PalindromeRequest request = new PalindromeRequest("user1", "");

        doThrow(new IllegalArgumentException("Text cannot be null or empty"))
                .when(mockValidationChain).validate("");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> palindromeService.processRequest(request));

        assertEquals("Text cannot be null or empty", exception.getMessage());
    }

    @Test
    void givenProcessedPalindrome_whenProcessedAgain_thenUsesCache() {
        // Given
        String text = "radar";
        PalindromeRequest request = new PalindromeRequest("user1", text);
        PalindromeResponse expectedResponse = new PalindromeResponse(true, text);

        when(mockModelMapper.map(request, PalindromeResponse.class)).thenReturn(expectedResponse);
        when(mockCache.get(text)).thenReturn(true);

        // When
        PalindromeResponse response = palindromeService.processRequest(request);

        // Then
        assertTrue(response.isPalindrome());
        verify(mockEventPublisher, never()).publishEvent(any());
    }
}