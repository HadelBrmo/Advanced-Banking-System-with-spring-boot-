package com.example.Advances.Banking.System.accounts;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountServices accountServices;

    public AccountController(AccountServices accountServices) {
        this.accountServices = accountServices;
    }


    @PostMapping("/{type}")
    public  Account createAccount(@PathVariable String type){
        return  accountServices.createAccount(type);
    }

    @GetMapping
    public List<Account> getAccount(){
        return  accountServices.getAllAccounts();
    }

    @PutMapping("/{id}/close")
    public  void closeAccount(@PathVariable Long id){
        accountServices.closeAccount(id);
    }
}
