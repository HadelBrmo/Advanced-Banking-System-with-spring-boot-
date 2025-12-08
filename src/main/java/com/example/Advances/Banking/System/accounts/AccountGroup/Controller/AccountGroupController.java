package com.example.Advances.Banking.System.accounts.AccountGroup.Controller;

import com.example.Advances.Banking.System.accounts.AccountGroup.Entity.AccountsGroup;
import com.example.Advances.Banking.System.accounts.AccountGroup.Services.AccountGroupServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountGroupController {
    private final AccountGroupServices accountGroupService;

    public AccountGroupController(AccountGroupServices accountGroupService) {
        this.accountGroupService = accountGroupService;
    }

    @PostMapping("/{ownerName}")
    public AccountsGroup createGroup(@PathVariable String ownerName) {
        return accountGroupService.createGroup(ownerName);
    }

    @PutMapping("/{groupId}/add-account/{accountId}")
    public AccountsGroup addAccountToGroup(@PathVariable Long groupId, @PathVariable Long accountId) {
        return accountGroupService.addAccountToGroup(groupId, accountId);
    }

    @GetMapping
    public List<AccountsGroup> getGroups() {
        return accountGroupService.getAllGroups();
    }
}
