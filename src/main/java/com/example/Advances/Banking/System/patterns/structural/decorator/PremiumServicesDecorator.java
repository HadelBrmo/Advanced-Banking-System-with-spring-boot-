package com.example.Advances.Banking.System.patterns.structural.decorator;

public class PremiumServicesDecorator extends AccountDecorator {

    public PremiumServicesDecorator(BankAccount account) {
        super(account);
    }

    @Override
    public String getDescription() {
        return decoratedAccount.getDescription() + " + خدمات مميزة";
    }

    @Override
    public double getMonthlyFee() {
        return decoratedAccount.getMonthlyFee() + 10.0;
    }

    public void getFinancialAdvice() {
        System.out.println("📊 استشارة مالية مجانية للعميل المميز");
    }

    public void requestPrioritySupport() {
        System.out.println("👑 خدمة عملاء مخصصة للعميل المميز");
    }

    public void getInvestmentOpportunities() {
        System.out.println("💎 عروض استثمارية حصرية للعميل المميز");
    }
}
