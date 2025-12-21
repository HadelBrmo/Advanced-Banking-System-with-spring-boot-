package com.example.Advances.Banking.System.subsystem.admin.dashboard;

import com.example.Advances.Banking.System.patterns.behavioral.state.BankAccount;
import java.util.List;

public interface MonitoringService {
    SystemDashboard snapshot(List<BankAccount> accounts, int dailyTransactionCount);
}