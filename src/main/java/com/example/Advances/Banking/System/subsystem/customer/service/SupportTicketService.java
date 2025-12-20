package com.example.Advances.Banking.System.subsystem.customer.service;

import com.example.Advances.Banking.System.core.enums.TicketCategory;
import com.example.Advances.Banking.System.core.model.Customer;
import com.example.Advances.Banking.System.subsystem.customer.model.SupportTicket;
import com.example.Advances.Banking.System.subsystem.customer.repository.SupportTicketRepository;
import com.example.Advances.Banking.System.patterns.behavioral.observer.NotificationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupportTicketService {

    private final SupportTicketRepository ticketRepository;
    private final NotificationManager notificationManager;

    public SupportTicketService(SupportTicketRepository ticketRepository,
                                NotificationManager notificationManager) {
        this.ticketRepository = ticketRepository;
        this.notificationManager = notificationManager;
    }



    public SupportTicket createTicket(Customer customer,
                                      TicketCategory category,
                                      String subject, String description) {

        System.out.println("🎫 Creating support ticket for customer: " + customer.getFullName());

        SupportTicket ticket = new SupportTicket(customer, category, subject, description);

        SupportTicket savedTicket = ticketRepository.save(ticket);

        sendTicketCreatedNotification(savedTicket);

        System.out.println("✅ Ticket created: " + savedTicket.getTicketNumber());
        return savedTicket;
    }

    public List<SupportTicket> getCustomerTickets(Long customerId) {
        return ticketRepository.findByCustomerId(customerId);
    }

    public Optional<SupportTicket> getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    public SupportTicket assignTicket(Long ticketId, String agentUsername) {
        SupportTicket ticket = findTicketOrThrow(ticketId);
        ticket.assignToAgent(agentUsername);

        return ticketRepository.save(ticket);
    }

    public SupportTicket addResponse(Long ticketId, String author,
                                     String message, boolean fromCustomer) {
        SupportTicket ticket = findTicketOrThrow(ticketId);
        ticket.addResponse(author, message, fromCustomer);

        return ticketRepository.save(ticket);
    }

    public SupportTicket resolveTicket(Long ticketId, String resolutionNotes) {
        SupportTicket ticket = findTicketOrThrow(ticketId);
        ticket.resolve(resolutionNotes);

        return ticketRepository.save(ticket);
    }

    public SupportTicket closeTicket(Long ticketId) {
        SupportTicket ticket = findTicketOrThrow(ticketId);
        ticket.close();

        return ticketRepository.save(ticket);
    }

    public long getTotalTicketCount() {
        return ticketRepository.count();
    }



    private SupportTicket findTicketOrThrow(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found: " + ticketId));
    }

    private void sendTicketCreatedNotification(SupportTicket ticket) {
        if (notificationManager != null && ticket.getCustomer() != null) {
            notificationManager.sendEvent(
                    ticket.getCustomer().getEmail(),
                    "TICKET_CREATED",
                    0.0,
                    "Ticket created: " + ticket.getTicketNumber()
            );
        }
    }
}