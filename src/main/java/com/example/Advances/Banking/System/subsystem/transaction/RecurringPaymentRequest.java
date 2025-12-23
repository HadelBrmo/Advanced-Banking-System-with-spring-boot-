package com.example.Advances.Banking.System.subsystem.transaction;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RecurringPaymentRequest {
    private String fromAccount;
    private String toAccount;
    private double amount;
    private String frequency;
    private LocalDate startDate;
    private int numberOfPayments;
    private String description;
}