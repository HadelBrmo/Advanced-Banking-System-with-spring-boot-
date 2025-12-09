package com.example.Advances.Banking.System.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BankingException.class)
    public ResponseEntity<Map<String, Object>> handleBankingException(BankingException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("error", ex.getErrorCode());
        response.put("message", ex.getMessage());
        response.put("status", ex.getHttpStatus());

        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("error", "INTERNAL_SERVER_ERROR");
        response.put("message", "An unexpected error occurred");
        response.put("status", 500);

        return ResponseEntity.status(500).body(response);
    }
}
