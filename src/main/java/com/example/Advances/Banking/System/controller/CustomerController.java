package com.example.Advances.Banking.System.controller;


import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private List<Customer> customers = new ArrayList<>();

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        customers.add(customer);
        System.out.println("✅ Customer created: " + customer.getFullName());
        return customer;
    }

    @PostMapping("/{customerId}/accounts/create")
    public Account createAccount(
            @PathVariable String customerId,
            @RequestParam AccountType accountType,
            @RequestParam double initialBalance) {

        Customer customer = customers.stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Account account = new Account(accountType, customer, initialBalance);
        customer.addAccount(account);

        System.out.println("✅ Account created: " + account.getAccountNumber());
        return account;
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customers;
    }
}