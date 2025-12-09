package com.example.Advances.Banking.System.core.model;


import com.example.Advances.Banking.System.core.enums.TransactionType;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
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
    private String status; // PENDING, COMPLETED, FAILED, CANCELLED

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    // ===== Constructors =====
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

    // ===== Business Methods =====
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

    // ===== Helper Methods =====
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

    // ===== Getters and Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Account getFromAccount() { return fromAccount; }
    public void setFromAccount(Account fromAccount) { this.fromAccount = fromAccount; }

    public Account getToAccount() { return toAccount; }
    public void setToAccount(Account toAccount) { this.toAccount = toAccount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
