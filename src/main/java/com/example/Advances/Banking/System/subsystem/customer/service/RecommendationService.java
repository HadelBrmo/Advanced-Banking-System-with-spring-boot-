//// RecommendationService.java - الخدمة الكاملة
//package com.example.Advances.Banking.System.subsystem.customer.service;
//
//import com.example.Advances.Banking.System.core.model.Customer;
//import com.example.Advances.Banking.System.subsystem.customer.model.*;
//import com.example.Advances.Banking.System.core.model.Transaction;
//import com.example.Advances.Banking.System.subsystem.transaction.service.TransactionService;
//import jakarta.transaction.Transactional;
//import org.springframework.stereotype.Service;
//import java.util.*;
//
//@Service
//@Transactional
//public class RecommendationService {
//
//    private final TransactionService transactionService;
//    private final AccountService accountService;
//    private final CustomerProfileService profileService;
//
//    public RecommendationService(TransactionService transactionService, AccountService accountService, CustomerProfileService profileService) {
//        this.transactionService = transactionService;
//        this.accountService = accountService;
//        this.profileService = profileService;
//    }
//
//    // ===== التوصيات المالية =====
//
//    public List<Recommendation> generateRecommendations(Long customerId) {
//        List<Recommendation> recommendations = new ArrayList<>();
//
//        // 1. تحليل عادات الإنفاق
//        recommendations.addAll(analyzeSpendingPatterns(customerId));
//
//        // 2. تحليل عادات الادخار
//        recommendations.addAll(analyzeSavingsPatterns(customerId));
//
//        // 3. فرص الاستثمار
//        recommendations.addAll(analyzeInvestmentOpportunities(customerId));
//
//        // 4. ترشيح منتجات بنكية
//        recommendations.addAll(suggestBankingProducts(customerId));
//
//        // 5. نصائح لإدارة الدين
//        recommendations.addAll(analyzeDebtManagement(customerId));
//
//        // 6. تحسين الائتمان
//        recommendations.addAll(analyzeCreditImprovement(customerId));
//
//        // ترتيب التوصيات حسب الأولوية
//        return recommendations.stream()
//                .sorted((r1, r2) -> getPriorityValue(r2.getPriority())
//                        - getPriorityValue(r1.getPriority()))
//                .collect(Collectors.toList());
//    }
//
//    private List<Recommendation> analyzeSpendingPatterns(Long customerId) {
//        List<Recommendation> recommendations = new ArrayList<>();
//
//        // الحصول على بيانات الإنفاق آخر 90 يوم
//        Map<String, Double> spendingByCategory = transactionService
//                .getSpendingByCategory(customerId, 90);
//
//        // 1. تحليل الإنفاق على المطاعم
//        Double restaurantSpending = spendingByCategory.getOrDefault("RESTAURANTS", 0.0);
//        Double foodSpending = spendingByCategory.getOrDefault("GROCERIES", 0.0);
//
//        if (restaurantSpending > 1000 && restaurantSpending > foodSpending * 2) {
//            Double potentialSavings = restaurantSpending * 0.3;
//
//            recommendations.add(new Recommendation(
//                    "SPENDING_OPTIMIZATION",
//                    "توفير في الإنفاق على المطاعم",
//                    "أنت تنفق " + restaurantSpending + " ريال شهرياً على المطاعم. " +
//                            "بالمقارنة، إنفاقك على البقالة هو " + foodSpending + " ريال فقط. " +
//                            "يمكنك توفير " + String.format("%.0f", potentialSavings) +
//                            " ريال شهرياً بالطهي المنزلي 3 أيام إضافية أسبوعياً.",
//                    "MEDIUM",
//                    potentialSavings,
//                    new Date(),
//                    generateActionPlan("توفير المطاعم", Arrays.asList(
//                            "خطط لـ 3 وجبات منزلية أسبوعياً",
//                            "استخدم كوبونات الخصم عند الذهاب للمطاعم",
//                            "اجعل المطاعم مناسبة خاصة وليست روتينية"
//                    ))
//            ));
//        }
//
//        // 2. تحليل الإنفاق على الترفيه
//        Double entertainmentSpending = spendingByCategory.getOrDefault("ENTERTAINMENT", 0.0);
//        Double monthlyIncome = profileService.getMonthlyIncome(customerId);
//
//        if (monthlyIncome > 0 && entertainmentSpending / monthlyIncome > 0.15) {
//            recommendations.add(new Recommendation(
//                    "ENTERTAINMENT_BUDGET",
//                    "ميزانية الترفيه مرتفعة",
//                    "إنفاقك على الترفيه يشكل " +
//                            String.format("%.1f", (entertainmentSpending/monthlyIncome)*100) +
//                            "% من دخلك الشهري. الحد الصحي هو 10-15%.",
//                    "LOW",
//                    entertainmentSpending * 0.2,
//                    new Date()
//            ));
//        }
//
//        // 3. اكتشاف الاشتراكات غير المستخدمة
//        List<String> recurringPayments = transactionService
//                .getRecurringPayments(customerId);
//
//        Set<String> usedServices = detectUsedServices(recurringPayments);
//        Set<String> allSubscriptions = getAllSubscriptions(recurringPayments);
//        allSubscriptions.removeAll(usedServices);
//
//        if (!allSubscriptions.isEmpty()) {
//            Double subscriptionSavings = calculateSubscriptionSavings(allSubscriptions);
//
//            recommendations.add(new Recommendation(
//                    "UNUSED_SUBSCRIPTIONS",
//                    "اشتراكات غير مستخدمة",
//                    "لديك " + allSubscriptions.size() + " اشتراكات غير مستخدمة: " +
//                            String.join(", ", allSubscriptions) + ". " +
//                            "يمكنك توفير " + subscriptionSavings + " ريال شهرياً بإلغائها.",
//                    "HIGH",
//                    subscriptionSavings,
//                    new Date(),
//                    generateActionPlan("إلغاء الاشتراكات", Arrays.asList(
//                            "راجع الاشتراكات الشهرية في بطاقتك",
//                            "ألغِ الخدمات التي لا تستخدمها منذ شهر",
//                            "فكر في حزم مجمعة بدلاً من اشتراكات فردية"
//                    ))
//            ));
//        }
//
//        return recommendations;
//    }
//
//    private List<Recommendation> analyzeSavingsPatterns(Long customerId) {
//        List<Recommendation> recommendations = new ArrayList<>();
//
//        Double monthlyIncome = profileService.getMonthlyIncome(customerId);
//        Double monthlySavings = transactionService.getMonthlySavings(customerId);
//
//        if (monthlyIncome > 0) {
//            Double savingsRate = monthlySavings / monthlyIncome;
//
//            // 1. توصية لزيادة الادخار
//            if (savingsRate < 0.2) {
//                Double targetSavings = monthlyIncome * 0.2;
//                Double additionalSavings = targetSavings - monthlySavings;
//
//                recommendations.add(new Recommendation(
//                        "INCREASE_SAVINGS",
//                        "زيادة معدل الادخار",
//                        "معدل ادخارك الحالي هو " + String.format("%.1f", savingsRate*100) +
//                                "%. المثالي هو 20%. يمكنك ادخار " +
//                                String.format("%.0f", additionalSavings) + " ريال إضافي شهرياً.",
//                        "HIGH",
//                        additionalSavings * 12, // توفير سنوي
//                        new Date(),
//                        generateActionPlan("خطة الادخار", Arrays.asList(
//                                "خصم 20% من الراتب مباشرة عند الاستلام",
//                                "افتح حساب توفير منفصل للطوارئ",
//                                "استخدم قاعدة 50-30-20: 50% حاجات، 30% رغبات، 20% ادخار"
//                        ))
//                ));
//            }
//
//            // 2. توصية بصندوق الطوارئ
//            Double emergencyFund = accountService.getEmergencyFundBalance(customerId);
//            Double monthlyExpenses = transactionService.getAverageMonthlyExpenses(customerId);
//
//            if (emergencyFund < monthlyExpenses * 3) {
//                Double neededAmount = (monthlyExpenses * 6) - emergencyFund;
//
//                recommendations.add(new Recommendation(
//                        "EMERGENCY_FUND",
//                        "بناء صندوق الطوارئ",
//                        "صندوق الطوارئ المثالي يساوي 3-6 أشهر من المصروفات. " +
//                                "أنت بحاجة إلى " + String.format("%.0f", neededAmount) +
//                                " ريال إضافي لتصل إلى 6 أشهر.",
//                        "URGENT",
//                        0.0, // قيمة وقائية
//                        new Date()
//                ));
//            }
//        }
//
//        // 3. توصية بالادخار التلقائي
//        recommendations.add(new Recommendation(
//                "AUTOMATIC_SAVINGS",
//                "الادخار التلقائي",
//                "اضبط تحويلاً تلقائياً من حسابك الجاري إلى حساب التوفير " +
//                        "عند استلام الراتب. الادخار التلقائي يزيد الالتزام بنسبة 40%.",
//                "MEDIUM",
//                monthlyIncome * 0.2 * 0.4, // زيادة الادخار المتوقعة
//                new Date()
//        ));
//
//        return recommendations;
//    }
//
//    private List<Recommendation> analyzeInvestmentOpportunities(Long customerId) {
//        List<Recommendation> recommendations = new ArrayList<>();
//
//        Double idleCash = accountService.getIdleCashBalance(customerId);
//        String riskProfile = profileService.getRiskProfile(customerId);
//        Integer age = profileService.getCustomerAge(customerId);
//
//        // 1. استثمار النقد العاطل
//        if (idleCash > 10000) {
//            String investmentType = suggestInvestmentType(riskProfile, age);
//            Double expectedReturn = calculateExpectedReturn(investmentType, idleCash);
//
//            recommendations.add(new Recommendation(
//                    "INVEST_IDLE_CASH",
//                    "استثمار النقد العاطل",
//                    "لديك " + String.format("%.0f", idleCash) + " ريال نقد عاطل. " +
//                            "يمكن استثمارها في " + investmentType + " بعائد متوقع " +
//                            String.format("%.1f", expectedReturn/idleCash*100) + "% سنوياً.",
//                    "MEDIUM",
//                    expectedReturn,
//                    new Date(),
//                    generateActionPlan("الاستثمار الأول", Arrays.asList(
//                            "ابدأ بـ 25% من المبلغ كتجربة",
//                            "نوّع بين أسهم وصناديق وسندات",
//                            "راجع استثماراتك كل ربع سنة"
//                    ))
//            ));
//        }
//
//        // 2. توصية بالتقاعد
//        if (age != null && age >= 25 && age <= 50) {
//            Double retirementSavings = accountService.getRetirementBalance(customerId);
//            Double recommendedAmount = calculateRecommendedRetirementSavings(age,
//                    profileService.getMonthlyIncome(customerId));
//
//            if (retirementSavings < recommendedAmount * 0.5) {
//                recommendations.add(new Recommendation(
//                        "RETIREMENT_PLANNING",
//                        "تخطيط التقاعد",
//                        "بناءً على عمرك ودخلك، يجب أن يكون لديك " +
//                                String.format("%.0f", recommendedAmount) + " ريال مدخر للتقاعد. " +
//                                "أنت عند " + String.format("%.0f%%", (retirementSavings/recommendedAmount)*100) +
//                                " من الهدف.",
//                        "HIGH",
//                        (recommendedAmount - retirementSavings) * 0.07, // عائد متوقع
//                        new Date()
//                ));
//            }
//        }
//
//        return recommendations;
//    }
//
//    private List<Recommendation> suggestBankingProducts(Long customerId) {
//        List<Recommendation> recommendations = new ArrayList<>();
//
//        CustomerProfile profile = profileService.getCustomerProfile(customerId);
//
//        // 1. بطاقة ائتمان مناسبة
//        if (profile.getCreditScore() > 700 &&
//                !accountService.hasPremiumCreditCard(customerId)) {
//
//            recommendations.add(new Recommendation(
//                    "PREMIUM_CREDIT_CARD",
//                    "ترقية بطاقة الائتمان",
//                    "بسبب درجة ائتمانك الممتازة (" + profile.getCreditScore() +
//                            ")، يمكنك الحصول على بطاقة ذهبية بمزايا: " +
//                            "نقاط مكافآت، تأمين سفر، وأسعار فائدة أفضل.",
//                    "MEDIUM",
//                    1500.0, // قيمة المكافآت السنوية التقريبية
//                    new Date()
//            ));
//        }
//
//        // 2. منتج تمويل
//        if (profile.hasFinancingNeed() &&
//                profile.getCreditScore() >= 650) {
//
//            recommendations.add(new Recommendation(
//                    "PERSONAL_LOAN",
//                    "تمويل شخصي بفوائد مخفضة",
//                    "بسبب سجل الدفع الجيد، مؤهّل للحصول على تمويل شخصي " +
//                            "بفائدة " + (profile.getCreditScore() > 750 ? "6%" : "8%") +
//                            " بدلاً من 12% في السوق.",
//                    "LOW",
//                    calculateInterestSavings(profile),
//                    new Date()
//            ));
//        }
//
//        // 3. حساب استثماري
//        if (profile.getTotalBalance() > 50000 &&
//                !accountService.hasInvestmentAccount(customerId)) {
//
//            recommendations.add(new Recommendation(
//                    "INVESTMENT_ACCOUNT",
//                    "حساب استثماري متقدم",
//                    "باستثمار " + String.format("%.0f", profile.getTotalBalance() * 0.3) +
//                            " ريال، يمكنك تحقيق عائد إضافي " +
//                            String.format("%.0f", profile.getTotalBalance() * 0.3 * 0.05) +
//                            " ريال سنوياً.",
//                    "MEDIUM",
//                    profile.getTotalBalance() * 0.3 * 0.05,
//                    new Date()
//            ));
//        }
//
//        return recommendations;
//    }
//
//    // ===== أدوات مساعدة =====
//
//    private ActionPlan generateActionPlan(String title, List<String> steps) {
//        ActionPlan plan = new ActionPlan();
//        plan.setTitle(title);
//        plan.setSteps(steps);
//        plan.setEstimatedTime("2-4 أسابيع");
//        plan.setDifficulty("متوسط");
//        return plan;
//    }
//
//    private int getPriorityValue(String priority) {
//        return switch (priority) {
//            case "URGENT" -> 4;
//            case "HIGH" -> 3;
//            case "MEDIUM" -> 2;
//            case "LOW" -> 1;
//            default -> 0;
//        };
//    }
//
//    private String suggestInvestmentType(String riskProfile, Integer age) {
//        if ("AGGRESSIVE".equals(riskProfile) && age != null && age < 40) {
//            return "صناديق النمو";
//        } else if ("MODERATE".equals(riskProfile)) {
//            return "محفظة متوازنة";
//        } else {
//            return "صناديق الدخل";
//        }
//    }
//}