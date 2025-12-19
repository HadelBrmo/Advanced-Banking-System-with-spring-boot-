package com.example.Advances.Banking.System.nfr.testing.singleton;


import com.example.Advances.Banking.System.patterns.creational.singleton.DatabaseConnection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SingletonIntegrationTest {

    @Test
    void testMultipleCallsReturnSameInstance() {
        DatabaseConnection[] connections = new DatabaseConnection[5];
        for (int i = 0; i < 5; i++) {
            connections[i] = DatabaseConnection.getInstance();
        }

        for (int i = 1; i < connections.length; i++) {
            assertSame(connections[0], connections[i], "All instances should be the same");
        }
    }
}
