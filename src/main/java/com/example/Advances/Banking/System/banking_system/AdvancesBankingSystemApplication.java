package com.example.Advances.Banking.System.banking_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdvancesBankingSystemApplication {

	public static void main(String[] args) {

        SpringApplication.run(AdvancesBankingSystemApplication.class, args);
        System.out.println("=========================================");
        System.out.println(" Advanced Banking System Started!");
        System.out.println(" URL: http://localhost:8080");
        System.out.println(" Database: MySQL");
        System.out.println(" " + new java.util.Date());
        System.out.println("=========================================");
	}

}
