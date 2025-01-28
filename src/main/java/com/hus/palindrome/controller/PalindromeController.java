package com.hus.palindrome.controller;

import com.hus.palindrome.dto.PalindromeRequest;
import com.hus.palindrome.dto.PalindromeResponse;
import com.hus.palindrome.service.PalindromeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/palindrome")
@Validated
public class PalindromeController {
    private final PalindromeService palindromeService;

    public PalindromeController(PalindromeService palindromeService) {
        this.palindromeService = palindromeService;
    }

    /**
     * Endpoint to process a palindrome check request.
     *
     * @param request the palindrome request payload.
     * @return ResponseEntity containing the palindrome response.
     */
    @PostMapping("/check")
    @Operation(summary = "Check if a text is a palindrome", description = "Validates and processes palindrome requests.")
    @ApiResponse(responseCode = "200", description = "Successfully processed the request")
    public ResponseEntity<PalindromeResponse> checkPalindrome(@Valid @RequestBody PalindromeRequest request) {
        PalindromeResponse response = palindromeService.processRequest(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint to confirm the API is up and running.
     *
     * @return A simple string message indicating API health.
     */
    @GetMapping("/health")
    @Operation(summary = "Health Check", description = "Returns the status of the API.")
    @ApiResponse(responseCode = "200", description = "API is up and running")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Palindrome API is up and running");
    }
}
