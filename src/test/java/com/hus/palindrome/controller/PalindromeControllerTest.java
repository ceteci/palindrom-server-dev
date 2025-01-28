package com.hus.palindrome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hus.palindrome.dto.PalindromeRequest;
import com.hus.palindrome.dto.PalindromeResponse;
import com.hus.palindrome.exception.BaseExceptionHandler;
import com.hus.palindrome.service.PalindromeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class PalindromeControllerTest {

    private MockMvc mockMvc;

    private PalindromeService palindromeService;

    @BeforeEach
    void setUp() {
        palindromeService = Mockito.mock(PalindromeService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PalindromeController(palindromeService))
                .setControllerAdvice(new BaseExceptionHandler())
                .build();
    }

    @Test
    void givenHealthEndpoint_whenCalled_thenReturnsHealthStatus() throws Exception {
        // Given: Health endpoint does not require any preparation.

        // When
        ResultActions result = mockMvc.perform(get("/api/palindrome/health"));

        // Then
        result.andExpect(status().isOk())
                .andExpect(content().string("Palindrome API is up and running"));
    }

    @Test
    void givenValidPalindromeRequest_whenProcessed_thenReturnsPalindromeResponse() throws Exception {
        // Given
        PalindromeRequest request = new PalindromeRequest("user1", "madam");
        PalindromeResponse response = new PalindromeResponse(true, "madam");
        Mockito.when(palindromeService.processRequest(Mockito.any(PalindromeRequest.class))).thenReturn(response);

        // When
        ResultActions result = mockMvc.perform(post("/api/palindrome/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)));

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.palindrome").value(true))
                .andExpect(jsonPath("$.text").value("madam"));
    }

    @Test
    void givenInvalidPalindromeRequest_whenProcessed_thenReturnsBadRequest() throws Exception {
        // Given
        PalindromeRequest request = new PalindromeRequest("user1", "madam123");
        Mockito.when(palindromeService.processRequest(Mockito.any(PalindromeRequest.class)))
                .thenThrow(new IllegalArgumentException("Input cannot contain numbers"));

        // When
        ResultActions result = mockMvc.perform(post("/api/palindrome/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Input cannot contain numbers")));
    }

    @Test
    void givenNullUsername_whenProcessed_thenReturnsBadRequest() throws Exception {
        // Given
        PalindromeRequest request = new PalindromeRequest(null, "madam");

        // When
        ResultActions result = mockMvc.perform(post("/api/palindrome/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Username is required")));
    }

    @ParameterizedTest
    @CsvSource({
            "madam, true",
            "keyboard, false",
            "radar, true"
    })
    void testPalindromeCheck(String input, boolean expected) throws Exception {
        // Given
        PalindromeRequest request = new PalindromeRequest("user1", input);
        PalindromeResponse response = new PalindromeResponse(expected, input);
        Mockito.when(palindromeService.processRequest(Mockito.any(PalindromeRequest.class))).thenReturn(response);

        // When
        ResultActions result = mockMvc.perform(post("/api/palindrome/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)));

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.palindrome").value(expected))
                .andExpect(jsonPath("$.text").value(input));
    }

}