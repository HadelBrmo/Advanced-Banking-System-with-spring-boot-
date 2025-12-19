package com.example.Advances.Banking.System.subsystem.customer.repository;

import com.example.Advances.Banking.System.core.enums.TicketStatus;
import com.example.Advances.Banking.System.subsystem.customer.model.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {


    SupportTicket findByTicketNumber(String ticketNumber);

    List<SupportTicket> findByCustomerId(Long customerId);

    List<SupportTicket> findByStatus(TicketStatus status);
}