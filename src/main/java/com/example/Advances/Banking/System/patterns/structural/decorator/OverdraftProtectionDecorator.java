package com.example.Advances.Banking.System.patterns.structural.decorator;

public class OverdraftProtectionDecorator extends AccountDecorator {

    private double overdraftLimit;
    private double overdraftFee;
    private double currentOverdraftUsed;

    public OverdraftProtectionDecorator(BankAccount account, double limit) {
        super(account);
        this.overdraftLimit = limit;
        this.overdraftFee = 5.0;
        this.currentOverdraftUsed = 0.0;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ مبلغ سحب غير صالح");
            return false;
        }

        double currentBalance = decoratedAccount.getBalance();
        double totalAvailable = currentBalance + overdraftLimit;

        if (amount <= totalAvailable && amount <= (currentBalance + (overdraftLimit - currentOverdraftUsed))) {
            if (currentBalance >= amount) {
                return decoratedAccount.withdraw(amount);
            } else {

                if (currentBalance > 0) {
                    decoratedAccount.withdraw(currentBalance);
                }

                double remainingAmount = amount - currentBalance;
                currentOverdraftUsed += remainingAmount;

                currentOverdraftUsed += overdraftFee;
                System.out.println("💸 رسوم سحب مكشوف: " + overdraftFee);

                return true;
            }
        }

        System.out.println("❌ تجاوز حد السحب المكشوف");
        return false;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            if (currentOverdraftUsed > 0) {
                if (amount >= currentOverdraftUsed) {
                    double remaining = amount - currentOverdraftUsed;
                    currentOverdraftUsed = 0;
                    if (remaining > 0) {
                        decoratedAccount.deposit(remaining);
                    }
                } else {
                    currentOverdraftUsed -= amount;
                }
            } else {
                decoratedAccount.deposit(amount);
            }
        }
    }

    @Override
    public double getBalance() {
        return decoratedAccount.getBalance() - currentOverdraftUsed;
    }

    @Override
    public String getDescription() {
        return decoratedAccount.getDescription() + " + حماية السحب المكشوف (حد: " + overdraftLimit + ")";
    }

    @Override
    public double getMonthlyFee() {
        return decoratedAccount.getMonthlyFee() + 2.0;
    }

    @Override
    public String getAccountNumber() {
        return decoratedAccount.getAccountNumber();
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getAvailableOverdraft() {
        return Math.max(0, overdraftLimit - currentOverdraftUsed);
    }

    public double getCurrentOverdraftUsed() {
        return currentOverdraftUsed;
    }

    public double getTotalAvailableBalance() {
        return decoratedAccount.getBalance() + overdraftLimit;
    }

    public double getRealBalance() {
        return decoratedAccount.getBalance();
    }
}