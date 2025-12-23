//package com.example.Advances.Banking.System.subsystem.admin.service;
//
//import com.example.Advances.Banking.System.core.model.Account;
//import com.example.Advances.Banking.System.patterns.creational.factory.AccountFactory;
//import com.example.Advances.Banking.System.subsystem.admin.service.AdminService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//
//    @Autowired
//    private AccountFactory accountFactory; // ⭐ استخدم Factory الموجود
//
//    @Autowired
//    private AccountStateFactory stateFactory; // ⭐ استخدم State Pattern
//
//    @Override
//    public Account createAccount(AccountRequest req) {
//        // استخدم Factory Pattern المطلوب
//        return accountFactory.createAccount(
//                req.getType(),
//                req.getCustomer(),
//                req.getInitialBalance()
//        );
//    }
//
//    @Override
//    public boolean changeState(String accountId, AccountStateType newState) {
//        Account account = accountRepository.findById(accountId)
//                .orElseThrow(() -> new AccountNotFoundException(accountId));
//
//        // استخدم State Pattern المطلوب
//        AccountState state = stateFactory.createState(newState);
//        account.setState(state);
//
//        accountRepository.save(account);
//
//        // أرسل إشعار باستخدام Observer Pattern
//        notificationService.notifyStateChange(account, newState);
//
//        return true;
//    }
//
//    @Override
//    public void createAccount(String accountId, String ownerName, String type) {
//
//    }
//
//    @Override
//    public void changeState(String accountId, String newState) {
//
//    }
//}