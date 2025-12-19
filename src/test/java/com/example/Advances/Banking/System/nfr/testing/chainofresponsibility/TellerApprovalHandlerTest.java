package com.example.Advances.Banking.System.nfr.testing.chainofresponsibility;
import com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility.ManagerApprovalHandler;
import com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility.TellerApprovalHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TellerApprovalHandlerTest {

    @Test
    void testTellerApprovesAmount() {
        TellerApprovalHandler teller = new TellerApprovalHandler();
        TestOutputCapture output = new TestOutputCapture();
        System.setOut(output.getPrintStream());

        teller.handleRequest(800);

        assertTrue(output.getOutput().contains("Teller approved amount: 800"));
    }

    @Test
    void testTellerPassesToNext() {
        TellerApprovalHandler teller = new TellerApprovalHandler();
        ManagerApprovalHandler manager = new ManagerApprovalHandler();
        teller.setNext(manager);

        TestOutputCapture output = new TestOutputCapture();
        System.setOut(output.getPrintStream());

        teller.handleRequest(3000);

        assertTrue(output.getOutput().contains("Manager approved amount: 3000"));
    }

    static class TestOutputCapture {
        private final java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        public java.io.PrintStream getPrintStream() { return new java.io.PrintStream(out); }
        public String getOutput() { return out.toString().trim(); }
    }
}
