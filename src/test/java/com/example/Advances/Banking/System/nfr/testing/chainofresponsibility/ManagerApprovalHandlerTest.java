package com.example.Advances.Banking.System.nfr.testing.chainofresponsibility;

import com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility.DirectorApprovalHandler;
import com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility.ManagerApprovalHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ManagerApprovalHandlerTest {

    @Test
    void testManagerApprovesAmount() {
        ManagerApprovalHandler manager = new ManagerApprovalHandler();
        TestOutputCapture output = new TestOutputCapture();
        System.setOut(output.getPrintStream());

        manager.handleRequest(4000);

        assertTrue(output.getOutput().contains("Manager approved amount: 4000"));
    }

    @Test
    void testManagerPassesToNext() {
        ManagerApprovalHandler manager = new ManagerApprovalHandler();
        DirectorApprovalHandler director = new DirectorApprovalHandler();
        manager.setNext(director);

        TestOutputCapture output = new TestOutputCapture();
        System.setOut(output.getPrintStream());

        manager.handleRequest(15000);

        assertTrue(output.getOutput().contains("Director approved amount: 15000"));
    }

    static class TestOutputCapture {
        private final java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        public java.io.PrintStream getPrintStream() { return new java.io.PrintStream(out); }
        public String getOutput() { return out.toString().trim(); }
    }
}