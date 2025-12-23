package com.example.Advances.Banking.System.subsystem.admin.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ReportRequest {

    @NotNull(message = "Report type is required")
    private ReportType reportType;

    private LocalDate startDate;
    private LocalDate endDate;

    private String accountType;
    private String customerId;

    @NotBlank(message = "Format is required")
    private String format; // JSON, PDF, CSV, EXCEL

    public enum ReportType {
        DAILY_TRANSACTIONS,
        ACCOUNT_SUMMARY,
        CUSTOMER_SUMMARY,
        AUDIT_LOG,
        SECURITY_REPORT,
        PERFORMANCE_REPORT
    }
}
