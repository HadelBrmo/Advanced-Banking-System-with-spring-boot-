package com.example.Advances.Banking.System.patterns.structural.decorator;


public class LoanProtectionDecorator extends AccountDecorator {

    private double protectionAmount;
    private boolean isActive;
    private double monthlyProtectionFee;

    public LoanProtectionDecorator(BankAccount account, double protectionAmount) {
        super(account);
        this.protectionAmount = protectionAmount;
        this.isActive = true;
        this.monthlyProtectionFee = 7.5;
    }

    @Override
    public String getDescription() {
        return decoratedAccount.getDescription() +
                " + Loan Protection (Coverage: " + protectionAmount + ")";
    }

    @Override
    public double getMonthlyFee() {
        return decoratedAccount.getMonthlyFee() +
                (isActive ? monthlyProtectionFee : 0);
    }


    public void activateProtection() {
        this.isActive = true;
        System.out.println("✅ Loan protection activated for account: " + getAccountNumber());
    }


    public void deactivateProtection() {
        this.isActive = false;
        System.out.println("⏸️ Loan protection deactivated for account: " + getAccountNumber());
    }


    public boolean fileProtectionClaim(double claimAmount, String reason) {
        if (!isActive) {
            System.out.println("❌ Loan protection is not active");
            return false;
        }

        if (claimAmount <= protectionAmount) {
            System.out.println(" Loan protection claim filed for: " + claimAmount +
                    " | Reason: " + reason);
            deposit(claimAmount); // Deposit the claimed amount into account
            return true;
        } else {
            System.out.println(" Claim amount exceeds protection coverage");
            return false;
        }
    }

    public double getRemainingCoverage() {
        return protectionAmount;
    }

    public void increaseCoverage(double additionalAmount) {
        this.protectionAmount += additionalAmount;
        this.monthlyProtectionFee += (additionalAmount * 0.001);
        System.out.println("📈 Loan protection increased by: " + additionalAmount);
    }

    @Override
    public boolean withdraw(double amount) {
        if (getDescription().contains("Loan") && amount > 10000) {
            System.out.println(" Large withdrawal from loan account requires approval");
        }
        return decoratedAccount.withdraw(amount);
    }
}
