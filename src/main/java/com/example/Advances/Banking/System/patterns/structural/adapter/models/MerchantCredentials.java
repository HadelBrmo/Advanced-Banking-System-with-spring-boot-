package com.example.Advances.Banking.System.patterns.structural.adapter.models;

import lombok.Getter;

@Getter
public class MerchantCredentials {
    private final String apiKey;
    private final String secretKey;

    public MerchantCredentials(String apiKey, String secretKey) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return "MerchantCredentials{apiKey='***" + apiKey.substring(Math.max(0, apiKey.length()-4))
                + "', secretKey='***" + secretKey.substring(Math.max(0, secretKey.length()-4)) + "'}";
    }
}