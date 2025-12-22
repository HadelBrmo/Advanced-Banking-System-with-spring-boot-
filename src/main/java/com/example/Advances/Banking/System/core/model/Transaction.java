package com.example.Advances.Banking.System.core.model;


import com.example.Advances.Banking.System.core.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    private String description;

    @Column(nullable = false)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Transaction() {
        this.transactionId = generateTransactionId();
        this.timestamp = new Date();
        this.status = "PENDING";
    }

    public Transaction(TransactionType type, double amount, Account fromAccount, Account toAccount) {
        this();
        this.type = type;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.description = generateDescription();
    }

    public Transaction(Double amount, String status) {
        this.transactionId = generateTransactionId();
        this.timestamp = new Date();
        this.amount = amount;
        this.status = status;
    }

    public void execute() {
        switch (type) {
            case DEPOSIT:
                if (toAccount == null) {
                    throw new IllegalArgumentException("Deposit requires a toAccount");
                }
                toAccount.deposit(amount);
                break;

            case WITHDRAWAL:
                if (fromAccount == null) {
                    throw new IllegalArgumentException("Withdrawal requires a fromAccount");
                }
                fromAccount.withdraw(amount);
                break;

            case TRANSFER:
                if (fromAccount == null || toAccount == null) {
                    throw new IllegalArgumentException("Transfer requires both fromAccount and toAccount");
                }
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
                break;

            default:
                throw new UnsupportedOperationException("Transaction type not supported: " + type);
        }

        this.status = "COMPLETED";
    }

    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis() + "-" +
                (int)(Math.random() * 1000);
    }

    private String generateDescription() {
        switch (type) {
            case DEPOSIT:
                return String.format("Deposit of $%.2f to account %s",
                        amount, toAccount.getAccountNumber());
            case WITHDRAWAL:
                return String.format("Withdrawal of $%.2f from account %s",
                        amount, fromAccount.getAccountNumber());
            case TRANSFER:
                return String.format("Transfer of $%.2f from %s to %s",
                        amount, fromAccount.getAccountNumber(), toAccount.getAccountNumber());
            default:
                return type.getDescription() + " transaction";
        }
    }

}