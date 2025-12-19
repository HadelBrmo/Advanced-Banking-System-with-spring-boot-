package com.example.Advances.Banking.System.nfr.testing.chainofresponsibility;

import com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility.AutoApprovalHandler;
import com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility.DirectorApprovalHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectorApprovalHandlerTest {

    @Test
    void testDirectorApprovesAmount() {
        DirectorApprovalHandler director = new DirectorApprovalHandler();
        TestOutputCapture output = new TestOutputCapture();
        System.setOut(output.getPrintStream());

        director.handleRequest(18000);

        assertTrue(output.getOutput().contains("Director approved amount: 18000"));
    }

    @Test
    void testDirectorPassesToNext() {
        DirectorApprovalHandler director = new DirectorApprovalHandler();
        AutoApprovalHandler auto = new AutoApprovalHandler();
        director.setNext(auto);

        TestOutputCapture output = new TestOutputCapture();
        System.setOut(output.getPrintStream());

        director.handleRequest(50000);

        assertTrue(output.getOutput().contains("Auto-approved amount: 50000"));
    }

    static class TestOutputCapture {
        private final java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        public java.io.PrintStream getPrintStream() { return new java.io.PrintStream(out); }
        public String getOutput() { return out.toString().trim(); }
    }
}