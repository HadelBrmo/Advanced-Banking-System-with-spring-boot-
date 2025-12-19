package com.example.Advances.Banking.System.nfr.testing.singleton;

import com.example.Advances.Banking.System.patterns.creational.singleton.DatabaseConnection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {

    @Test
    void testSingletonInstanceIsSame() {
        DatabaseConnection conn1 = DatabaseConnection.getInstance();
        DatabaseConnection conn2 = DatabaseConnection.getInstance();
        assertSame(conn1, conn2, "Both instances should be the same");
    }

    @Test
    void testConnectionMethods() {
        DatabaseConnection conn = DatabaseConnection.getInstance();
        conn.connect();
        conn.disconnect();
        assertNotNull(conn);
    }
}