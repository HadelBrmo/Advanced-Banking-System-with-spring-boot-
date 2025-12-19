//package com.example.Advances.Banking.System.subsystem.customer.controller;
//
//import com.example.Advances.Banking.System.core.enums.TicketCategory;
//import com.example.Advances.Banking.System.core.model.Customer;
//import com.example.Advances.Banking.System.subsystem.customer.model.SupportTicket;
//import com.example.Advances.Banking.System.subsystem.customer.service.SupportTicketService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/support/tickets")
//public class SupportTicketController {
//
//    @Autowired
//    private SupportTicketService ticketService;
//
//    @PostMapping("/create")
//    public ResponseEntity<?> createTicket(@RequestBody CreateTicketRequest request) {
//        try {
//            Customer dummyCustomer = createDummyCustomer(request.getCustomerId());
//
//            SupportTicket ticket = ticketService.createTicket(
//                    dummyCustomer,
//                    request.getCategory(),
//                    request.getSubject(),
//                    request.getDescription()
//            );
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("ticketNumber", ticket.getTicketNumber());
//            response.put("message", "تم إنشاء التذكرة بنجاح");
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("success", false, "error", e.getMessage()));
//        }
//    }
//
//    @GetMapping("/customer/{customerId}")
//    public ResponseEntity<?> getCustomerTickets(@PathVariable Long customerId) {
//        try {
//            List<SupportTicket> tickets = ticketService.getCustomerTickets(customerId);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("tickets", tickets);
//            response.put("count", tickets.size());
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("success", false, "error", e.getMessage()));
//        }
//    }
//
//    @GetMapping("/{ticketNumber}")
//    public ResponseEntity<?> getTicket(@PathVariable String ticketNumber) {
//        try {
//            SupportTicket ticket = ticketService.getTicketByNumber(ticketNumber);
//
//            if (ticket == null) {
//                return ResponseEntity.notFound().build();
//            }
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("ticket", ticket);
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("success", false, "error", e.getMessage()));
//        }
//    }
//
//    @PostMapping("/{ticketId}/assign")
//    public ResponseEntity<?> assignTicket(@PathVariable Long ticketId,
//                                          @RequestParam String agentUsername) {
//        try {
//            SupportTicket ticket = ticketService.assignTicket(ticketId, agentUsername);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("ticket", ticket);
//            response.put("message", "تم تعيين التذكرة للموظف: " + agentUsername);
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("success", false, "error", e.getMessage()));
//        }
//    }
//
//    @PostMapping("/{ticketId}/respond")
//    public ResponseEntity<?> addResponse(@PathVariable Long ticketId,
//                                         @RequestBody AddResponseRequest request) {
//        try {
//            SupportTicket ticket = ticketService.addResponse(
//                    ticketId,
//                    request.getAuthor(),
//                    request.getMessage(),
//                    request.isFromCustomer()
//            );
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("ticket", ticket);
//            response.put("message", "تم إضافة الرد بنجاح");
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("success", false, "error", e.getMessage()));
//        }
//    }
//
//    @PostMapping("/{ticketId}/resolve")
//    public ResponseEntity<?> resolveTicket(@PathVariable Long ticketId,
//                                           @RequestParam String resolutionNotes) {
//        try {
//            SupportTicket ticket = ticketService.resolveTicket(ticketId, resolutionNotes);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("ticket", ticket);
//            response.put("message", "تم حل التذكرة بنجاح");
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("success", false, "error", e.getMessage()));
//        }
//    }
//
//    @GetMapping("/stats")
//    public ResponseEntity<?> getTicketStats() {
//        try {
//            Map<String, Object> stats = new HashMap<>();
//            stats.put("totalTickets", ticketService.getTotalTicketCount());
//            stats.put("openTickets", ticketService.getOpenTicketCount());
//            stats.put("avgResolutionTime", ticketService.getAverageResolutionTime());
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("stats", stats);
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("success", false, "error", e.getMessage()));
//        }
//    }
//
//    public static class CreateTicketRequest {
//        private Long customerId;
//        private TicketCategory category;
//        private String subject;
//        private String description;
//
//        // Getters and Setters
//        public Long getCustomerId() { return customerId; }
//        public void setCustomerId(Long customerId) { this.customerId = customerId; }
//
//        public TicketCategory getCategory() { return category; }
//        public void setCategory(TicketCategory category) { this.category = category; }
//
//        public String getSubject() { return subject; }
//        public void setSubject(String subject) { this.subject = subject; }
//
//        public String getDescription() { return description; }
//        public void setDescription(String description) { this.description = description; }
//    }
//
//    public static class AddResponseRequest {
//        private String author;
//        private String message;
//        private boolean fromCustomer;
//
//        // Getters and Setters
//        public String getAuthor() { return author; }
//        public void setAuthor(String author) { this.author = author; }
//
//        public String getMessage() { return message; }
//        public void setMessage(String message) { this.message = message; }
//
//        public boolean isFromCustomer() { return fromCustomer; }
//        public void setFromCustomer(boolean fromCustomer) { this.fromCustomer = fromCustomer; }
//    }
//
//    private Customer createDummyCustomer(Long customerId) {
//        Customer customer = new Customer();
//        customer.setId(customerId);
//        customer.setFirstName("Customer");
//        customer.setLastName(customerId.toString());
//        customer.setEmail("customer" + customerId + "@example.com");
//        return customer;
//    }
//}