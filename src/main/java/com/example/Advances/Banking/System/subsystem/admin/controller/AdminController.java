package com.example.Advances.Banking.System.subsystem.admin.controller;

import com.example.Advances.Banking.System.subsystem.admin.dto.AccountRequest;
import com.example.Advances.Banking.System.subsystem.admin.dto.AccountStateChangeRequest;
import com.example.Advances.Banking.System.subsystem.admin.service.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/accounts")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@RequestBody AccountRequest req) {
        adminService.createAccount(req.accountId, req.ownerName, req.type);
        return "Created " + req.accountId;
    }

    @PostMapping("/accounts/state")
    @PreAuthorize("hasRole('ADMIN')")
    public String changeState(@RequestBody AccountStateChangeRequest req) {
        adminService.changeState(req.accountId, req.newState);
        return "State changed";
    }
}