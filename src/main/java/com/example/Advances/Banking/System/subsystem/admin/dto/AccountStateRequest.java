package com.example.Advances.Banking.System.subsystem.admin.dto;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountStateRequest {

    @NotBlank(message = "Account number is required")
    @Size(min = 10, max = 20, message = "Account number must be between 10 and 20 characters")
    private String accountNumber;

    @NotNull(message = "New status is required")
    private AccountStatus newStatus;

    @NotBlank(message = "Reason is required for state changes")
    @Size(min = 10, max = 500, message = "Reason must be between 10 and 500 characters")
    private String reason;

    private boolean notifyCustomer = true;
}