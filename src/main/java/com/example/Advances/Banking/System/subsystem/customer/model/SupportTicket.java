package com.example.Advances.Banking.System.subsystem.customer.model;

import com.example.Advances.Banking.System.core.enums.TicketCategory;
import com.example.Advances.Banking.System.core.enums.TicketPriority;
import com.example.Advances.Banking.System.core.enums.TicketStatus;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.core.model.TicketResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "support_tickets")
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String ticketNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @Column(nullable = false)
    private String subject;

    @Column(length = 2000, nullable = false)
    private String description;

    private String assignedTo;
    private String resolutionNotes;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date resolvedAt;

    @ElementCollection
    @CollectionTable(name = "ticket_responses", joinColumns = @JoinColumn(name = "ticket_id"))
    private List<TicketResponse> responses = new ArrayList<>();


    public SupportTicket() {
        this.ticketNumber = generateTicketNumber();
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.status = TicketStatus.OPEN;
        this.priority = TicketPriority.MEDIUM;
    }

    public SupportTicket(Customer customer, TicketCategory category,
                         String subject, String description) {
        this();
        this.customer = customer;
        this.category = category;
        this.subject = subject;
        this.description = description;
        determinePriority();
    }

    private String generateTicketNumber() {
        return "TICKET-" + System.currentTimeMillis() + "-" +
                (int)(Math.random() * 1000);
    }

    private void determinePriority() {
        if (category == TicketCategory.TRANSACTION_ISSUE ||
                category == TicketCategory.FRAUD_ALERT) {
            this.priority = TicketPriority.HIGH;
        } else if (category == TicketCategory.ACCOUNT_ISSUE) {
            this.priority = TicketPriority.MEDIUM;
        } else {
            this.priority = TicketPriority.LOW;
        }
    }

    public void assignToAgent(String agentUsername) {
        this.assignedTo = agentUsername;
        this.status = TicketStatus.IN_PROGRESS;
        this.updatedAt = new Date();

        addResponse("SYSTEM", "Ticket assigned to agent: " + agentUsername, false);
    }

    public void addResponse(String author, String message, boolean fromCustomer) {
        TicketResponse response = new TicketResponse(author, message, fromCustomer);
        this.responses.add(response);
        this.updatedAt = new Date();
    }

    public void resolve(String resolutionNotes) {
        this.status = TicketStatus.RESOLVED;
        this.resolutionNotes = resolutionNotes;
        this.resolvedAt = new Date();
        this.updatedAt = new Date();

        addResponse("SYSTEM", "Ticket resolved: " + resolutionNotes, false);
    }

    public void close() {
        if (this.status != TicketStatus.RESOLVED) {
            throw new IllegalStateException("Ticket must be resolved before closing");
        }
        this.status = TicketStatus.CLOSED;
        this.updatedAt = new Date();
    }

    //تصعيد
    public void escalate() {
        if (this.priority == TicketPriority.LOW) {
            this.priority = TicketPriority.MEDIUM;
        } else if (this.priority == TicketPriority.MEDIUM) {
            this.priority = TicketPriority.HIGH;
        } else if (this.priority == TicketPriority.HIGH) {
            this.priority = TicketPriority.URGENT;
        }
        this.updatedAt = new Date();

        addResponse("SYSTEM", "Ticket escalated to " + this.priority, false);
    }

    public boolean isOpen() {
        return status == TicketStatus.OPEN || status == TicketStatus.IN_PROGRESS;
    }


    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    @Override
    public String toString() {
        return String.format("SupportTicket{ticketNumber='%s', subject='%s', status=%s}",
                ticketNumber, subject, status);
    }
}