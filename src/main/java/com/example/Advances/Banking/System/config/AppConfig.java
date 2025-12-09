package com.example.Advances.Banking.System.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Bean
    public String appName() {
        return "Advanced Banking System";
    }

    @Bean
    public String appVersion() {
        return "1.0.0";
    }
}
