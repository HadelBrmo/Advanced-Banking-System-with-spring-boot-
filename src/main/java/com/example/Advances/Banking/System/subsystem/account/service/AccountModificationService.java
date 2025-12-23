package com.example.Advances.Banking.System.subsystem.account.service;

import com.example.Advances.Banking.System.core.enums.AccountStatus;
import com.example.Advances.Banking.System.core.model.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class AccountModificationService {

        public void modifyAccountSettings(Account account, AccountModificationRequest request) {
            System.out.println("✏️ Modifying account: " + account.getAccountNumber());


            if (!canModifyAccount(account)) {
                throw new IllegalStateException("Cannot modify account. Status: " + account.getStatus());
            }


            if (request.getNewMinBalance() != null) {
                account.setMinBalance(request.getNewMinBalance());
                System.out.println("  → Min balance updated to: $" + request.getNewMinBalance());
            }

            if (request.getNewMaxWithdrawal() != null) {
                account.setMaxDailyWithdrawal(request.getNewMaxWithdrawal());
                System.out.println("  → Max daily withdrawal updated to: $" + request.getNewMaxWithdrawal());
            }

            if (request.getOverdraftLimit() != null) {
                if (account.getHasOverdraft()) {
                    account.setOverdraftLimit(request.getOverdraftLimit());
                    System.out.println("  → Overdraft limit set to: $" + request.getOverdraftLimit());
                } else {
                    System.out.println("  ⚠️ Account doesn't have overdraft feature");
                }
            }

            System.out.println("✅ Account modification completed");
        }


        public void changeAccountStatus(Account account, AccountStatus newStatus, String reason) {
            System.out.println("🔄 Changing account status from " +
                    account.getStatus() + " to " + newStatus);

            validateStatusTransition(account.getStatus(), newStatus);

            account.setStatus(newStatus);
            System.out.println("  → Reason: " + reason);
            System.out.println("✅ Account status changed successfully");
        }


        public void updateContactInfo(Account account, String newEmail, String newPhone) {
            System.out.println(" Updating contact info for account: " + account.getAccountNumber());

            if (newEmail != null && !newEmail.isEmpty()) {
                account.getCustomer().setEmail(newEmail);
                System.out.println("  → Email updated to: " + newEmail);
            }

            if (newPhone != null && !newPhone.isEmpty()) {
                account.getCustomer().setPhone(newPhone);
                System.out.println("  → Phone updated to: " + newPhone);
            }

            System.out.println("✅ Contact info updated");
        }


        private boolean canModifyAccount(Account account) {

            return account.getStatus() == AccountStatus.ACTIVE;
        }

        private void validateStatusTransition(AccountStatus current, AccountStatus newStatus) {

            if (current == AccountStatus.CLOSED && newStatus == AccountStatus.ACTIVE) {
                throw new IllegalStateException("Cannot reactivate a closed account");
            }
        }
    }


    @Setter
    @Getter
    class AccountModificationRequest {
        private Double newMinBalance;
        private Double newMaxWithdrawal;
        private Double overdraftLimit;


    }

