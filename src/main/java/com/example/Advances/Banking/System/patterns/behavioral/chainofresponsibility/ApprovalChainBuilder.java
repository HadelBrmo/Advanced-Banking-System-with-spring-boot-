package com.example.Advances.Banking.System.patterns.behavioral.chainofresponsibility;

public class ApprovalChainBuilder {
    public static ApprovalHandler buildChain() {
        ApprovalHandler teller = new TellerApprovalHandler();
        ApprovalHandler manager = new ManagerApprovalHandler();
        ApprovalHandler director = new DirectorApprovalHandler();
        ApprovalHandler auto = new AutoApprovalHandler();

        teller.setNext(manager);
        manager.setNext(director);
        director.setNext(auto);

        return teller;
    }
}
