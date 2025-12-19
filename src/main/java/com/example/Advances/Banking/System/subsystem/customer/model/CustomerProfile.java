package com.example.Advances.Banking.System.subsystem.customer.model;

import com.example.Advances.Banking.System.core.model.Customer;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "customer_profiles")
public class CustomerProfile {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String riskProfile;
    private String financialGoals;

    @ElementCollection
    @CollectionTable(name = "customer_spending_categories")
    private Map<String, Double> monthlySpendingByCategory = new HashMap<>();

    private Double averageMonthlyIncome;
    private Double averageMonthlySavings;
    private Date lastAnalysisDate;

    // Constructors, Getters, Setters
}
