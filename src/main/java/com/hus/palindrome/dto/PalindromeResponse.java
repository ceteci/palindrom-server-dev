package com.hus.palindrome.dto;

public class PalindromeResponse {
    private boolean isPalindrome;
    private String text;

    // ModelMapper needs no-args constructor
    public PalindromeResponse() {
    }

    public PalindromeResponse(boolean isPalindrome, String text) {
        this.isPalindrome = isPalindrome;
        this.text = text;
    }

    // Getters and Setters

    public boolean isPalindrome() {
        return isPalindrome;
    }

    public void setPalindrome(boolean palindrome) {
        isPalindrome = palindrome;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
