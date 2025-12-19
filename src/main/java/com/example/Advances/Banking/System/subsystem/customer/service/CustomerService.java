//package com.example.Advances.Banking.System.subsystem.customer.service;
//
//import com.example.Advances.Banking.System.core.model.Customer;
//import com.example.Advances.Banking.System.patterns.behavioral.observer.NotificationManager;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomerService {
//    private final SupportTicketService ticketService;
//    private final RecommendationService recommendationService;
//    private final NotificationManager notificationManager;
//
//    public CustomerService(SupportTicketService ticketService,
//                           RecommendationService recommendationService) {
//        this.ticketService = ticketService;
//        this.recommendationService = recommendationService;
//        this.notificationManager = new NotificationManager();
//    }
//
//    // دمج جميع خدمات العميل
//    public CustomerDashboard getCustomerDashboard(Long customerId) {
//        CustomerDashboard dashboard = new CustomerDashboard();
//
//        // 1. التذاكر المفتوحة
//        dashboard.setOpenTickets(ticketService.getOpenTickets(customerId));
//
//        // 2. التوصيات
//        dashboard.setRecommendations(
//                recommendationService.generateRecommendations(customerId)
//        );
//
//        // 3. الإشعارات الأخيرة
//        dashboard.setRecentNotifications(
//                getRecentNotifications(customerId)
//        );
//
//        return dashboard;
//    }
//}
