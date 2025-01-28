package com.hus.palindrome.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogUtilsTest {

    @Test
    void givenStartPhase_whenCreateStructuredLog_thenContainsExpectedFields() {
        // Given
        String phase = "START";
        String layer = "SERVICE";
        String methodName = "processRequest";
        Object[] args = {"arg1", 123};
        Object result = null;
        long execTimeMs = 0;

        // When
        String logEntry = LogUtils.createStructuredLog(phase, layer, methodName, args, result, execTimeMs);

        // Then
        assertTrue(logEntry.contains("\"phase\":\"START\""));
        assertTrue(logEntry.contains("\"layer\":\"SERVICE\""));
        assertTrue(logEntry.contains("\"method\":\"processRequest\""));
        assertTrue(logEntry.contains("\"arguments\":[\"arg1\",123]"));
    }

    @Test
    void givenEndPhase_whenCreateStructuredLog_thenContainsExecutionTimeAndResult() {
        // Given
        String phase = "END";
        String layer = "CONTROLLER";
        String methodName = "processRequest";
        Object[] args = {"user1", "madam"};
        Object result = true;
        long execTimeMs = 150;

        // When
        String logEntry = LogUtils.createStructuredLog(phase, layer, methodName, args, result, execTimeMs);

        // Then
        assertTrue(logEntry.contains("\"phase\":\"END\""));
        assertTrue(logEntry.contains("\"layer\":\"CONTROLLER\""));
        assertTrue(logEntry.contains("\"method\":\"processRequest\""));
        assertTrue(logEntry.contains("\"arguments\":[\"user1\",\"madam\"]"));
        assertTrue(logEntry.contains("\"result\":true"));
        assertTrue(logEntry.contains("\"executionTimeMs\":150"));
    }
}