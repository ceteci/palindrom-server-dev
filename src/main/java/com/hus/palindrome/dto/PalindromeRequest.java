package com.hus.palindrome.dto;

import jakarta.validation.constraints.NotNull;

public class PalindromeRequest {
    @NotNull(message = "Username is required.")
    private String username;

    @NotNull(message = "Text is required.")
    private String text;

    // ModelMapper needs no-args constructor
    public PalindromeRequest() {
    }

    public PalindromeRequest(String username, String text) {
        this.username = username;
        this.text = text;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
