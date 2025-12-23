package com.example.Advances.Banking.System.subsystem.admin.dto;


import com.example.Advances.Banking.System.core.enums.AccountType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AccountCreateRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name can only contain letters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name can only contain letters")
    private String lastName;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "Initial balance is required")
    @DecimalMin(value = "0.0", message = "Initial balance cannot be negative")
    @DecimalMax(value = "1000000.0", message = "Initial balance cannot exceed 1,000,000")
    private Double initialBalance;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String phone;

    @Size(max = 200, message = "Address cannot exceed 200 characters")
    private String address;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;
}
