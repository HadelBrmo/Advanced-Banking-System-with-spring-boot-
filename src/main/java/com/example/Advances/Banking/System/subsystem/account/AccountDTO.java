package com.example.Advances.Banking.System.subsystem.account;

import com.example.Advances.Banking.System.core.model.Account;

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
public class AccountDTO {
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance;
    private String accountType;
    private String email;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public static AccountDTO fromEntity(Account account) {
        return AccountDTO.builder()
                .accountNumber(account.getAccountNumber())
                .accountHolderName(account.getCustomer() != null ?
                        account.getCustomer().getFullName() : "Unknown")

                .balance(BigDecimal.valueOf(account.getBalance()))

                .accountType(account.getAccountType() != null ?
                        account.getAccountType().name() : "UNKNOWN")

                .email(account.getCustomer() != null ?
                        account.getCustomer().getEmail() : null)

                .isActive(account.getStatus() != null &&
                        account.getStatus().name().equals("ACTIVE"))

                .createdAt(account.getCreatedAt() != null ?
                        account.getCreatedAt().toInstant()
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDateTime() : null)
                .build();
    }
}