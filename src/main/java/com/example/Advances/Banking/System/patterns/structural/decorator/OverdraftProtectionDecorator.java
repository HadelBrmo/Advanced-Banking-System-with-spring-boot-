package com.example.Advances.Banking.System.patterns.structural.decorator;

//السحب المكشوف
public class OverdraftProtectionDecorator extends AccountDecorator {

    private double overdraftLimit;
    private double overdraftFee;

    public OverdraftProtectionDecorator(BankAccount account, double limit) {
        super(account);
        this.overdraftLimit = limit;
        this.overdraftFee = 5.0;
    }

    @Override
    public boolean withdraw(double amount) {
        double availableBalance = getBalance() + overdraftLimit;

        if (amount <= availableBalance) {

            boolean success = decoratedAccount.withdraw(amount);

            if (getBalance() < 0) {
                System.out.println("💸 رسوم سحب مكشوف: " + overdraftFee);
                decoratedAccount.withdraw(overdraftFee);
            }

            return success;
        }

        System.out.println("❌ تجاوز حد السحب المكشوف");
        return false;
    }

    @Override
    public String getDescription() {
        return decoratedAccount.getDescription() + " + حماية السحب المكشوف (حد: " + overdraftLimit + ")";
    }

    @Override
    public double getMonthlyFee() {
        return decoratedAccount.getMonthlyFee() + 2.0;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getAvailableOverdraft() {
        return overdraftLimit + getBalance();
    }
}
