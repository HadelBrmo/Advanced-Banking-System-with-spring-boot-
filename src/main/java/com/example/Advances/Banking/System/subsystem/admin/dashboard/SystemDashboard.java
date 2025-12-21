package com.example.Advances.Banking.System.subsystem.admin.dashboard;

public class SystemDashboard {
    public final int totalAccounts;
    public final int frozenAccounts;
    public final double totalBalance;
    public final int dailyTransactionCount;

    public SystemDashboard(int total, int frozen, double balance, int txCount) {
        this.totalAccounts = total;
        this.frozenAccounts = frozen;
        this.totalBalance = balance;
        this.dailyTransactionCount = txCount;
    }
}