package com.example.Advances.Banking.System.subsystem.customer.model;

import com.example.Advances.Banking.System.core.enums.TicketCategory;
import com.example.Advances.Banking.System.core.enums.TicketPriority;
import com.example.Advances.Banking.System.core.enums.TicketStatus;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.core.model.TicketResponse;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public TicketCategory getCategory() { return category; }
    public void setCategory(TicketCategory category) { this.category = category; }

    public TicketPriority getPriority() { return priority; }
    public void setPriority(TicketPriority priority) { this.priority = priority; }

    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    public String getResolutionNotes() { return resolutionNotes; }
    public void setResolutionNotes(String resolutionNotes) { this.resolutionNotes = resolutionNotes; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public Date getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(Date resolvedAt) { this.resolvedAt = resolvedAt; }

    public List<TicketResponse> getResponses() { return responses; }
    public void setResponses(List<TicketResponse> responses) { this.responses = responses; }

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