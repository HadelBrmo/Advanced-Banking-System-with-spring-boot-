package com.example.Advances.Banking.System.banking_system;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class HikariCPTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testHikariCPIsConfigured() {
        System.out.println("🧪 بدء اختبار تكوين HikariCP...");

        assertInstanceOf(HikariDataSource.class, dataSource,
                "DataSource يجب أن تكون HikariDataSource");

        System.out.println("✅ تم تأكيد أن DataSource هي HikariDataSource");

        HikariDataSource hikariDataSource = (HikariDataSource) dataSource;

        assertEquals(20, hikariDataSource.getMaximumPoolSize(),
                "maximum-pool-size يجب أن تكون 20");
        System.out.println("✅ maximum-pool-size: " + hikariDataSource.getMaximumPoolSize());

        assertEquals(10, hikariDataSource.getMinimumIdle(),
                "minimum-idle يجب أن تكون 10");
        System.out.println("✅ minimum-idle: " + hikariDataSource.getMinimumIdle());

        assertEquals(30000, hikariDataSource.getConnectionTimeout(),
                "connection-timeout يجب أن تكون 30000ms");
        System.out.println("✅ connection-timeout: " + hikariDataSource.getConnectionTimeout());

        System.out.println("📊 معلومات HikariCP الإضافية:");
        System.out.println("   - Pool Name: " + hikariDataSource.getPoolName());
        System.out.println("   - JDBC URL: " + hikariDataSource.getJdbcUrl());
        System.out.println("   - Username: " + hikariDataSource.getUsername());

        System.out.println("🔗 جرب الاتصال بقاعدة البيانات...");
        assertDoesNotThrow(() -> {
            var connection = dataSource.getConnection();
            assertTrue(connection.isValid(5));
            System.out.println("✅ الاتصال ناجح وصالح");
            connection.close();
        }, "يجب أن يكون الاتصال مع قاعدة البيانات ناجحاً");

        System.out.println("🎉 اختبار تكوين HikariCP اكتمل بنجاح!");
    }

    @Test
    void testConnectionPoolPerformance() throws Exception {
        System.out.println("⚡ بدء اختبار أداء Connection Pool...");

        HikariDataSource hikariDataSource = (HikariDataSource) dataSource;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            var connection = hikariDataSource.getConnection();
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("SELECT 1");
            if (resultSet.next()) {
                assertEquals(1, resultSet.getInt(1));
            }
            connection.close();
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        assertTrue(duration < 500,
                "الحصول على 10 اتصالات يجب أن يستغرق أقل من 500ms, لكن استغرق " + duration + "ms");

        System.out.println("✅ اختبار HikariCP: 10 اتصالات خلال " + duration + "ms");

        System.out.println("📊 إحصائيات البول بعد الاختبار:");
        System.out.println("   - Active Connections: " + hikariDataSource.getHikariPoolMXBean().getActiveConnections());
        System.out.println("   - Idle Connections: " + hikariDataSource.getHikariPoolMXBean().getIdleConnections());
        System.out.println("   - Total Connections: " + hikariDataSource.getHikariPoolMXBean().getTotalConnections());
    }

    @Test
    void testDataSourceProperties() {
        System.out.println("🔧 اختبار خصائص DataSource...");

        HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
        assertNotNull(hikariDataSource.getJdbcUrl());
        assertTrue(hikariDataSource.getJdbcUrl().contains("bankdb"),
                "URL يجب أن يحتوي على bankdb");

        assertEquals("root", hikariDataSource.getUsername());

        System.out.println("✅ خصائص DataSource مضبوطة بشكل صحيح");
    }
}