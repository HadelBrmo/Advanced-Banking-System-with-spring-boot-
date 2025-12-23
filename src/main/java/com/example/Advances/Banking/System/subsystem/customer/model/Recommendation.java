package com.example.Advances.Banking.System.subsystem.customer.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Recommendation {
    private String type;
    private String title;
    private String description;
    private String priority;
    private Date generatedDate;
    private Double estimatedValue;

    public Recommendation(Double estimatedValue, Date generatedDate,
      String priority, String description, String title, String type) {
        this.estimatedValue = estimatedValue;
        this.generatedDate = generatedDate;
        this.priority = priority;
        this.description = description;
        this.title = title;
        this.type = type;
    }
}