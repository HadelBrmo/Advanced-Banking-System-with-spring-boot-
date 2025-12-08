package com.example.Advances.Banking.System.accounts.Accounts.Controller;

import com.example.Advances.Banking.System.accounts.Accounts.Entity.Account;
import com.example.Advances.Banking.System.accounts.Accounts.Services.AccountServices;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountServices accountService;

    public AccountController(AccountServices accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{type}")
    public Account createAccount(@PathVariable String type) {

        return accountService.createAccount(type);
    }

    @PutMapping("/{id}/update")
    public Account updateAccount(@PathVariable Long id, @RequestBody Account updatedAccount) {
        return accountService.updateAccount(id, updatedAccount);
    }

    @GetMapping
    public List<Account> getAccounts() {
        return accountService.getAllAccounts();
    }

    @PutMapping("/{id}/close")
    public Account closeAccount(@PathVariable Long id) {
        return accountService.closeAccount(id);
    }

    @PostMapping("/savings")
    public Account createSavingsAccount() {
        return accountService.createAccount("savings");
    }

    @PostMapping("/checking")
    public Account createCheckingAccount() {
        return accountService.createAccount("checking");
    }

    @PostMapping("/loan")
    public Account createLoanAccount() {
        return accountService.createAccount("loan");
    }

    @PostMapping("/investment")
    public Account createInvestmentAccount() {
        return accountService.createAccount("investment");
    }
}