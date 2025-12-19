//package com.example.Advances.Banking.System.subsystem.customer.service;
//
//
//import com.example.Advances.Banking.System.core.model.Customer;
//import com.example.Advances.Banking.System.subsystem.customer.model.CustomerProfile;
//import com.example.Advances.Banking.System.core.model.Transaction;
//import com.example.Advances.Banking.System.subsystem.customer.model.Recommendation;
//import org.springframework.stereotype.Service;
//import java.util.*;
//
//@Service
//public class RecommendationService {
//
//    public List<Recommendation> generateRecommendations(Customer customer) {
//        List<Recommendation> recommendations = new ArrayList<>();
//
//        // 1. تحليل عادات الإنفاق
//        analyzeSpendingPatterns(customer, recommendations);
//
//        // 2. تحليل عادات الادخار
//        analyzeSavingsPatterns(customer, recommendations);
//
//        // 3. فرص الاستثمار
//        analyzeInvestmentOpportunities(customer, recommendations);
//
//        // 4. ترشيح منتجات بنكية
//        suggestBankingProducts(customer, recommendations);
//
//        return recommendations;
//    }
//
//    private void analyzeSpendingPatterns(Customer customer,
//                                         List<Recommendation> recommendations) {
//        // إذا كان ينفق كثيراً على المطاعم
//        recommendations.add(new Recommendation(
//                "SPENDING_ALERT",
//                "High restaurant spending detected",
//                "Consider cooking at home to save $" + calculatePotentialSavings(),
//                "MEDIUM"
//        ));
//    }
//
//    private void analyzeSavingsPatterns(Customer customer,
//                                        List<Recommendation> recommendations) {
//        // إذا كان يدخر أقل من 20% من الدخل
//        recommendations.add(new Recommendation(
//                "SAVINGS_TIP",
//                "Increase your savings rate",
//                "Try to save at least 20% of your monthly income",
//                "LOW"
//        ));
//    }
//}
