package com.example.Advances.Banking.System.core.model;

import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public  class TicketResponse {
    private String author;
    private String message;
    private boolean fromCustomer;
    private Date timestamp;

    public TicketResponse() {
        this.timestamp = new Date();
    }

    public TicketResponse(String author, String message, boolean fromCustomer) {
        this();
        this.author = author;
        this.message = message;
        this.fromCustomer = fromCustomer;
    }

    // Getters and Setters
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isFromCustomer() { return fromCustomer; }
    public void setFromCustomer(boolean fromCustomer) { this.fromCustomer = fromCustomer; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}