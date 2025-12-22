package com.example.Advances.Banking.System.core.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Embeddable
public  class TicketResponse {

    //المؤلف/المرسل: الشخص الذي كتب الرد على التذكرة
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
}