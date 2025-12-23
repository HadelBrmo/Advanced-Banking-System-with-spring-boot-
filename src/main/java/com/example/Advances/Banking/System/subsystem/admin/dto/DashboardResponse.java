package com.example.Advances.Banking.System.subsystem.admin.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class DashboardResponse {
    private SystemMetrics metrics;
    private RecentActivity activity;
    private Map<String, Object> alerts;

    @Data
    @Builder
    public static class SystemMetrics {
        private int totalAccounts;
        private int activeAccounts;
        private int frozenAccounts;
        private int suspendedAccounts;
        private int closedAccounts;
        private double totalBalance;
        private int totalCustomers;
        private int dailyTransactions;
        private int weeklyTransactions;
        private int monthlyTransactions;
    }

    @Data
    @Builder
    public static class RecentActivity {
        private int recentLogins;
        private int recentTransactions;
        private int pendingApprovals;
        private int securityAlerts;
    }
}
