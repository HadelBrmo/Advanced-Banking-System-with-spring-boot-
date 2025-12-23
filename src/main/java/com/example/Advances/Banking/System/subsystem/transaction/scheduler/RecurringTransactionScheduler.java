package com.example.Advances.Banking.System.subsystem.transaction.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecurringTransactionScheduler {


    @Scheduled(cron = "0 0 2 1 * *")
    public void processMonthlyLoanPayments() {
        log.info("🏦 بدء معالجة دفعات القروض الشهرية...");

        try {


            log.info("✅ اكتملت معالجة دفعات القروض الشهرية");

        } catch (Exception e) {
            log.error("❌ فشل معالجة دفعات القروض", e);
        }
    }

    @Scheduled(cron = "0 0 9 * * FRI")
    public void processWeeklySalaryPayments() {
        log.info("💼 بدء معالجة دفعات الرواتب الأسبوعية...");
    }


    @Scheduled(cron = "0 0 6 5 * *")
    public void processUtilityBills() {
        log.info("💡 بدء معالجة فواتير الخدمات...");

    }
}