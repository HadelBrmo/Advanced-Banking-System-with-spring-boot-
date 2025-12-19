package com.example.Advances.Banking.System.nfr.testing.chainofresponsibility;


import com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility.AutoApprovalHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AutoApprovalHandlerTest {

    @Test
    void testAutoApprovesAnyAmount() {
        AutoApprovalHandler auto = new AutoApprovalHandler();
        TestOutputCapture output = new TestOutputCapture();
        System.setOut(output.getPrintStream());

        auto.handleRequest(99999);

        assertTrue(output.getOutput().contains("Auto-approved amount: 99999"));
    }

    static class TestOutputCapture {
        private final java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        public java.io.PrintStream getPrintStream() { return new java.io.PrintStream(out); }
        public String getOutput() { return out.toString().trim(); }
    }
}