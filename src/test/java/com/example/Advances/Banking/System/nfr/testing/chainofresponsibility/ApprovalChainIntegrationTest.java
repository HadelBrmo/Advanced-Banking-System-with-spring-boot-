package com.example.Advances.Banking.System.nfr.testing.chainofresponsibility;


import com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility.ApprovalChainBuilder;
import com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility.ApprovalHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ApprovalChainIntegrationTest {

    @Test
    void testFullChainApprovalFlow() {
        ApprovalHandler chain = ApprovalChainBuilder.buildChain();
        TestOutputCapture output = new TestOutputCapture();
        System.setOut(output.getPrintStream());

        chain.handleRequest(500);    // Teller
        chain.handleRequest(3000);   // Manager
        chain.handleRequest(15000);  // Director
        chain.handleRequest(50000);  // Auto

        String result = output.getOutput();

        assertTrue(result.contains("Teller approved amount: 500"));
        assertTrue(result.contains("Manager approved amount: 3000"));
        assertTrue(result.contains("Director approved amount: 15000"));
        assertTrue(result.contains("Auto-approved amount: 50000"));
    }

    static class TestOutputCapture {
        private final java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        public java.io.PrintStream getPrintStream() { return new java.io.PrintStream(out); }
        public String getOutput() { return out.toString().trim(); }
    }
}