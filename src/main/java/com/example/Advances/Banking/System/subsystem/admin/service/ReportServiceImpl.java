package com.example.Advances.Banking.System.subsystem.admin.service;

import com.example.Advances.Banking.System.patterns.behavioral.state.BankAccount;

import java.util.List;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {

    @Override
    public String generateAccountSummary(List<BankAccount> accounts) {
        double total = accounts.stream().mapToDouble(BankAccount::getBalance).sum();
        return "Account Summary -> Count: " + accounts.size() + ", Total Balance: " + total;
    }

    @Override
    public String generateDailyTransactionReport(String date, List<String> transactions) {
        String header = "Daily Transactions (" + date + ")";
        String items = transactions.stream()
                .map(t -> "- " + t)
                .collect(Collectors.joining("\n"));
        return header + "\n" + items;
    }

    @Override
    public String generateAuditLogReport(List<String> auditEvents) {
        String header = "Audit Log Report";
        String items = auditEvents.stream()
                .map(e -> "- " + e)
                .collect(Collectors.joining("\n"));
        return header + "\n" + items;
    }
}