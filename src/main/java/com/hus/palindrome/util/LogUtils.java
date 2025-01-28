package com.hus.palindrome.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

public class LogUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // structured logging is used to produce machine-readable log entries in a JSON format
    // this format is easily parsed by log aggregation tools (e.g., ElasticSearch, LogStash)
    // additionally, it enables filtering and searching logs based on fields like 'method', 'layer', 'executionTimeMs'
    public static String createStructuredLog(String phase, String layer, String methodName, Object[] args, Object result, long execTimeMs) {
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("traceId", MDC.get("traceId")); // Retrieve trace ID for distributed tracing
        logEntry.put("phase", phase);
        logEntry.put("layer", layer);
        logEntry.put("method", methodName);
        logEntry.put("arguments", args);
        if ("END".equals(phase)) {
            logEntry.put("result", result);
            logEntry.put("executionTimeMs", execTimeMs);
        }

        try {
            return objectMapper.writeValueAsString(logEntry);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to generate JSON log entry", e);
        }
    }
}
