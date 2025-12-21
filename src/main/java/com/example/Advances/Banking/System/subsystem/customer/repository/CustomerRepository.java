package com.example.Advances.Banking.System.subsystem.customer.repository;

import com.example.Advances.Banking.System.core.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}