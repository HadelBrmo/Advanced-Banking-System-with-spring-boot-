package com.example.Advances.Banking.System.subsystem.admin.service;

import com.example.Advances.Banking.System.patterns.behavioral.state.BankAccount;

import java.util.List;

public interface ReportService {
    String generateAccountSummary(List<BankAccount> accounts);
    String generateDailyTransactionReport(String date, List<String> transactions);
    String generateAuditLogReport(List<String> auditEvents);
}