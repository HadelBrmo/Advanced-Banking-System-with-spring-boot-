package com.example.Advances.Banking.System.subsystem.transaction.service;


import com.example.Advances.Banking.System.core.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private String transactionId;
    private String transactionType;
    private BigDecimal amount;
    private String fromAccountNumber;
    private String toAccountNumber;
    private String description;
    private String status;
    private LocalDateTime timestamp;

    public static TransactionDTO fromEntity(Transaction transaction) {
        return TransactionDTO.builder()
                .transactionId(transaction.getTransactionId())
                .transactionType(transaction.getType() != null ?
                        transaction.getType().name() : "UNKNOWN")
                .amount(BigDecimal.valueOf(transaction.getAmount()))
                .fromAccountNumber(transaction.getFromAccount() != null ?
                        transaction.getFromAccount().getAccountNumber() : null)
                .toAccountNumber(transaction.getToAccount() != null ?
                        transaction.getToAccount().getAccountNumber() : null)
                .description(transaction.getDescription())
                .status(transaction.getStatus())
                .timestamp(transaction.getTimestamp() != null ?
                        transaction.getTimestamp().toInstant()
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDateTime() : null)
                .build();
    }
}
