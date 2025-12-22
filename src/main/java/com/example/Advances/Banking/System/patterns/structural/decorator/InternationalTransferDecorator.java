package com.example.Advances.Banking.System.patterns.structural.decorator;

import java.util.Currency;
import java.util.Map;
import java.util.HashMap;

public class InternationalTransferDecorator extends AccountDecorator {

    private boolean internationalEnabled;
    private final Map<String, Double> exchangeRates; // Currency code -> exchange rate
    private final double internationalTransferFee;
    private double dailyInternationalLimit;
    private double monthlyInternationalUsed;

    public InternationalTransferDecorator(BankAccount account) {
        super(account);
        this.internationalEnabled = true;
        this.internationalTransferFee = 15.0;
        this.dailyInternationalLimit = 5000.0;
        this.monthlyInternationalUsed = 0.0;

        this.exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.92);
        exchangeRates.put("GBP", 0.79);
        exchangeRates.put("JPY", 148.0);
        exchangeRates.put("AED", 3.67);
        exchangeRates.put("SAR", 3.75);
    }

    @Override
    public String getDescription() {
        return decoratedAccount.getDescription() +
                " + International Transfers";
    }

    @Override
    public double getMonthlyFee() {
        return decoratedAccount.getMonthlyFee() + 5.0;
    }

    public boolean internationalTransfer(double amount, String targetAccount,
                                         String targetCurrency, String targetCountry) {

        if (!internationalEnabled) {
            System.out.println("❌ International transfers are disabled");
            return false;
        }

        if (amount <= 0) {
            System.out.println("❌ Transfer amount must be positive");
            return false;
        }

        if (amount > dailyInternationalLimit) {
            System.out.println("❌ Exceeds daily international limit: " + dailyInternationalLimit);
            return false;
        }

        if (monthlyInternationalUsed + amount > dailyInternationalLimit * 30) {
            System.out.println("❌ Exceeds monthly international limit");
            return false;
        }

        double amountInUSD = amount;
        if (!targetCurrency.equals("USD")) {
            Double exchangeRate = exchangeRates.get(targetCurrency.toUpperCase());
            if (exchangeRate == null) {
                System.out.println("❌ Unsupported currency: " + targetCurrency);
                return false;
            }
            amountInUSD = amount / exchangeRate;
        }

        double totalDeduction = amountInUSD + internationalTransferFee;
        if (!withdraw(totalDeduction)) {
            System.out.println("❌ Insufficient funds for international transfer");
            return false;
        }

        monthlyInternationalUsed += amountInUSD;

        System.out.println("🌍 International transfer completed:");
        System.out.println("   Amount: " + amount + " " + targetCurrency);
        System.out.println("   Equivalent: " + amountInUSD + " USD");
        System.out.println("   Fee: " + internationalTransferFee + " USD");
        System.out.println("   To: " + targetAccount + " (" + targetCountry + ")");
        System.out.println("   Monthly international used: " + monthlyInternationalUsed + " USD");

        return true;
    }


    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        Double fromRate = exchangeRates.get(fromCurrency.toUpperCase());
        Double toRate = exchangeRates.get(toCurrency.toUpperCase());

        if (fromRate == null || toRate == null) {
            System.out.println("❌ Unsupported currency");
            return 0.0;
        }

        double amountInUSD = amount / fromRate;
        double convertedAmount = amountInUSD * toRate;

        System.out.println("💱 Currency conversion:");
        System.out.println("   " + amount + " " + fromCurrency + " = " +
                convertedAmount + " " + toCurrency);

        return convertedAmount;
    }


    public Double getExchangeRate(String currencyCode) {
        return exchangeRates.get(currencyCode.toUpperCase());
    }

    public void addCurrency(String currencyCode, double exchangeRate) {
        exchangeRates.put(currencyCode.toUpperCase(), exchangeRate);
        System.out.println("➕ Added currency: " + currencyCode + " = " + exchangeRate);
    }

    public void setInternationalEnabled(boolean enabled) {
        this.internationalEnabled = enabled;
        System.out.println(enabled ?
                "✅ International transfers enabled" :
                "⏸️ International transfers disabled");
    }


    public void setDailyInternationalLimit(double limit) {
        this.dailyInternationalLimit = limit;
        System.out.println("📊 Daily international limit set to: " + limit);
    }


    public void resetMonthlyUsage() {
        this.monthlyInternationalUsed = 0.0;
        System.out.println("🔄 Monthly international usage reset");
    }


}