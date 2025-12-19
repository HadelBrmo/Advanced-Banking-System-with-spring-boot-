package com.example.Advances.Banking.System.admin_test;

import com.example.Advances.Banking.System.patterns.behavioral.state.*;
import com.example.Advances.Banking.System.subsystem.admin.service.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ReportServiceImplTest {

    @Test
    void generateAccountSummaryWorks() {
        ReportService reports = new ReportServiceImpl();
        BankAccount a1 = new BankAccount(); a1.setBalance(100);
        BankAccount a2 = new BankAccount(); a2.setBalance(200);
        String summary = reports.generateAccountSummary(Arrays.asList(a1, a2));
        assertTrue(summary.contains("Count: 2"));
        assertTrue(summary.contains("Total Balance: 300.0"));
    }

    @Test
    void generateDailyTransactionReportWorks() {
        ReportService reports = new ReportServiceImpl();
        List<String> tx = Arrays.asList("A1 deposit 100", "A2 withdraw 50");
        String daily = reports.generateDailyTransactionReport("2025-12-20", tx);
        assertTrue(daily.contains("Daily Transactions"));
        assertTrue(daily.contains("A1 deposit 100"));
    }

    @Test
    void generateAuditLogReportWorks() {
        ReportService reports = new ReportServiceImpl();
        List<String> audit = Arrays.asList("Account A1 created", "A2 state changed to FROZEN");
        String log = reports.generateAuditLogReport(audit);
        assertTrue(log.contains("Audit Log"));
        assertTrue(log.contains("A1 created"));
    }
}