package com.example.Advances.Banking.System.patterns.structural.adapter.models;

import lombok.Getter;

@Getter
public class TransactionStatus {
    private final String status;
    private final long timestamp;
    private final String externalTransactionId;

    public TransactionStatus(String status, long timestamp) {
        this(status, timestamp, null);
    }

    public TransactionStatus(String status, long timestamp, String externalTransactionId) {
        this.status = status;
        this.timestamp = timestamp;
        this.externalTransactionId = externalTransactionId;
    }


    public boolean isCompleted() {
        return "COMPLETED".equals(status) || "SUCCESS".equals(status);
    }

    public boolean isPending() {
        return "PENDING".equals(status);
    }

    public boolean isFailed() {
        return "FAILED".equals(status);
    }
}