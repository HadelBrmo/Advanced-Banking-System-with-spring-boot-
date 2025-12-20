//package com.example.Advances.Banking.System.controller;
//
//import com.zaxxer.hikari.HikariDataSource;
//import com.zaxxer.hikari.pool.HikariPoolMXBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/hikari")
//public class HikariController {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @GetMapping("/pool-stats")
//    public Map<String, Object> getPoolStats() {
//        Map<String, Object> stats = new HashMap<>();
//
//        if (dataSource instanceof HikariDataSource) {
//            HikariDataSource hikari = (HikariDataSource) dataSource;
//            HikariPoolMXBean pool = hikari.getHikariPoolMXBean();
//
//            stats.put("poolName", hikari.getPoolName());
//            stats.put("activeConnections", pool.getActiveConnections());
//            stats.put("idleConnections", pool.getIdleConnections());
//            stats.put("totalConnections", pool.getTotalConnections());
//            stats.put("threadsAwaitingConnection", pool.getThreadsAwaitingConnection());
//            stats.put("maximumPoolSize", hikari.getMaximumPoolSize());
//            stats.put("minimumIdle", hikari.getMinimumIdle());
//            stats.put("connectionTimeout", hikari.getConnectionTimeout());
//            stats.put("idleTimeout", hikari.getIdleTimeout());
//            stats.put("maxLifetime", hikari.getMaxLifetime());
//
//            // حساب النسبة المئوية
//            double usagePercentage = (double) pool.getActiveConnections() / hikari.getMaximumPoolSize() * 100;
//            stats.put("poolUsagePercentage", String.format("%.2f%%", usagePercentage));
//
//            // حالة الـ Pool
//            if (usagePercentage > 80) {
//                stats.put("status", "HIGH_USAGE");
//                stats.put("message", "استخدام عالي للـ Connection Pool، فكر في زيادة maximum-pool-size");
//            } else if (usagePercentage > 50) {
//                stats.put("status", "MODERATE_USAGE");
//                stats.put("message", "استخدام معتدل");
//            } else {
//                stats.put("status", "LOW_USAGE");
//                stats.put("message", "استخدام منخفض");
//            }
//        } else {
//            stats.put("error", "DataSource ليست HikariCP");
//        }
//
//        return stats;
//    }
//}