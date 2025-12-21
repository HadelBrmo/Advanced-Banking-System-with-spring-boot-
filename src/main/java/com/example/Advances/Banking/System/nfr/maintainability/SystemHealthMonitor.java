package com.example.Advances.Banking.System.nfr.maintainability;


import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class SystemHealthMonitor {

    private final DataSource dataSource;

    @Scheduled(fixedRate = 60000)
    // وظيفة رئيسية: مراقبة صحة النظام كل دقيقة
    public void monitorSystemHealth() {
        checkDatabaseHealth();
        checkMemoryUsage();
        checkThreadHealth();
        logSystemStatus();
    }

    private void checkDatabaseHealth() {
        try {
            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
            int activeConnections = hikariDataSource.getHikariPoolMXBean().getActiveConnections();
            int idleConnections = hikariDataSource.getHikariPoolMXBean().getIdleConnections();

            log.debug("📊 Database Health - Active: {}, Idle: {}",
                    activeConnections, idleConnections);

            if (activeConnections >= hikariDataSource.getMaximumPoolSize() * 0.9) {
                log.warn("⚠️ Database connections approaching limit: {}/{}",
                        activeConnections, hikariDataSource.getMaximumPoolSize());
            }
        } catch (Exception e) {
            log.error("❌ Database health check failed", e);
        }
    }

    private void checkMemoryUsage() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        long usedHeap = memoryBean.getHeapMemoryUsage().getUsed();
        long maxHeap = memoryBean.getHeapMemoryUsage().getMax();
        double usagePercentage = (usedHeap * 100.0) / maxHeap;

        log.debug("🧠 Memory Usage - {}% of {}MB",
                String.format("%.2f", usagePercentage),
                maxHeap / (1024 * 1024));

        if (usagePercentage > 80) {
            log.warn("⚠️ High memory usage: {}%", String.format("%.2f", usagePercentage));
        }
    }

    private void checkThreadHealth() {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        int threadCount = threadBean.getThreadCount();
        int daemonThreadCount = threadBean.getDaemonThreadCount();

        log.debug("🧵 Thread Health - Total: {}, Daemon: {}",
                threadCount, daemonThreadCount);
    }

    private void logSystemStatus() {
        log.info("🏥 System Health Check - {}", LocalDateTime.now());
    }
}