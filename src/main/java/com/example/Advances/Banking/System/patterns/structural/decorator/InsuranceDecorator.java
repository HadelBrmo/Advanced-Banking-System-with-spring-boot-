package com.example.Advances.Banking.System.patterns.structural.decorator;

//كلاس للتغطية التامينية اذا صارت مشكلة بالحساب تبعك
public class InsuranceDecorator extends AccountDecorator {

    private double coverageAmount;

    public InsuranceDecorator(BankAccount account, double coverage) {
        super(account);
        this.coverageAmount = coverage;
    }

    @Override
    public boolean withdraw(double amount) {
        return decoratedAccount.withdraw(amount);
    }

    @Override
    public String getDescription() {
        return decoratedAccount.getDescription() + " + تأمين على الحساب (تغطية: " + coverageAmount + ")";
    }

    @Override
    public double getMonthlyFee() {
        return decoratedAccount.getMonthlyFee() + 3.0;
    }

    public void fileClaim(double claimAmount) {
        if (claimAmount <= coverageAmount) {
            System.out.println("🛡️ تقديم مطالبة تأمينية: " + claimAmount);
            deposit(claimAmount);
        } else {
            System.out.println("❌ مبلغ المطالبة يتجاوز التغطية التأمينية");
        }
    }
}
