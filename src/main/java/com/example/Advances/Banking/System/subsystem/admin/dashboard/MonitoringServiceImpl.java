package com.example.Advances.Banking.System.subsystem.admin.dashboard;

import com.example.Advances.Banking.System.patterns.behavioral.state.BankAccount;
import com.example.Advances.Banking.System.patterns.behavioral.state.FrozenState;
import java.util.List;

public class MonitoringServiceImpl implements MonitoringService {
    @Override
    public SystemDashboard snapshot(List<BankAccount> accounts, int dailyTransactionCount) {
        int total = accounts.size();
        int frozen = (int) accounts.stream()
                .filter(a -> a.getState() instanceof FrozenState)
                .count();
        double sum = accounts.stream()
                .mapToDouble(BankAccount::getBalance)
                .sum();
        return new SystemDashboard(total, frozen, sum, dailyTransactionCount);
    }
}