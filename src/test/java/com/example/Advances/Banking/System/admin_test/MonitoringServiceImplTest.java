package com.example.Advances.Banking.System.admin_test;

import com.example.Advances.Banking.System.patterns.behavioral.state.*;
import com.example.Advances.Banking.System.subsystem.admin.dashboard.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class MonitoringServiceImplTest {

    @Test
    void snapshotCountsAreCorrect() {
        BankAccount a1 = new BankAccount(); a1.setBalance(100);
        BankAccount a2 = new BankAccount(); a2.setBalance(200); a2.setState(new FrozenState());
        MonitoringService mon = new MonitoringServiceImpl();
        SystemDashboard s = mon.snapshot(Arrays.asList(a1, a2), 5);
        assertEquals(2, s.totalAccounts);
        assertEquals(1, s.frozenAccounts);
        assertEquals(300.0, s.totalBalance);
        assertEquals(5, s.dailyTransactionCount);
    }
}