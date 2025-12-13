package com.example.Advances.Banking.System.nfr.testability;

import com.example.Advances.Banking.System.core.enums.AccountType;
import com.example.Advances.Banking.System.core.model.Account;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.patterns.creational.factory.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountFactoryImplTest {

    @Mock
    private AccountCreator mockSavingsCreator;

    @Mock
    private AccountCreator mockCheckingCreator;

    private AccountFactoryImpl accountFactory;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("Ali", "Hassan", "ali@test.com");

        when(mockSavingsCreator.supports(any(AccountType.class)))
                .thenAnswer(invocation -> {
                    AccountType type = invocation.getArgument(0);
                    return type == AccountType.SAVINGS;
                });

        when(mockCheckingCreator.supports(any(AccountType.class)))
                .thenAnswer(invocation -> {
                    AccountType type = invocation.getArgument(0);
                    return type == AccountType.CHECKING;
                });


        List<AccountCreator> creators = List.of(mockSavingsCreator, mockCheckingCreator);

        Map<AccountType, AccountCreator> creatorMap = new HashMap<>();
        creatorMap.put(AccountType.SAVINGS, mockSavingsCreator);
        creatorMap.put(AccountType.CHECKING, mockCheckingCreator);

        // استخدم reflection لتعيين الحقل مباشرة
        accountFactory = new AccountFactoryImpl(creators);
        try {
            var field = AccountFactoryImpl.class.getDeclaredField("creators");
            field.setAccessible(true);
            field.set(accountFactory, creatorMap);
        } catch (Exception e) {
        }
    }

    @Test
    void createAccount_ForSavings_ShouldUseSavingsCreator() {
        // Given
        Account mockAccount = new Account();
        when(mockSavingsCreator.create(testCustomer, 5000.0)).thenReturn(mockAccount);

        // When
        Account result = accountFactory.createAccount(AccountType.SAVINGS, testCustomer, 5000.0);

        // Then
        assertSame(mockAccount, result);
        verify(mockSavingsCreator, times(1)).create(testCustomer, 5000.0);
    }

    @Test
    void createAccount_ForChecking_ShouldUseCheckingCreator() {
        // Given
        Account mockAccount = new Account();
        when(mockCheckingCreator.create(testCustomer, 3000.0)).thenReturn(mockAccount);

        // When
        Account result = accountFactory.createAccount(AccountType.CHECKING, testCustomer, 3000.0);

        // Then
        assertSame(mockAccount, result);
        verify(mockCheckingCreator, times(1)).create(testCustomer, 3000.0);
    }
}