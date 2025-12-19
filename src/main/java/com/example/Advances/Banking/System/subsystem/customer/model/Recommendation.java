package com.example.Advances.Banking.System.subsystem.customer.model;

import java.util.Date;

public class Recommendation {
    private String type;
    private String title;
    private String description;
    private String priority;
    private Date generatedDate;

    public Double getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(Double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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