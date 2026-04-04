package com.finance.dashboard.dashboard_service.service.impl;

import com.finance.dashboard.dashboard_service.dto.DashboardSummaryDTO;
import com.finance.dashboard.dashboard_service.service.DashboardService;
import com.finance.dashboard.finance_service.entity.FinancialRecord;
import com.finance.dashboard.finance_service.entity.TransactionType;
import com.finance.dashboard.finance_service.repository.FinancialRecordRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final FinancialRecordRepository financialRecordRepository;

    public DashboardServiceImpl(FinancialRecordRepository financialRecordRepository) {
        this.financialRecordRepository = financialRecordRepository;
    }

    @Override
    public DashboardSummaryDTO getSummary(Long userId) {

        List<FinancialRecord> records = financialRecordRepository.findByUserId(userId);

        double income = records.stream()
                .filter(r -> r.getType() == TransactionType.INCOME)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double expense = records.stream()
                .filter(r -> r.getType() == TransactionType.EXPENSE)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        DashboardSummaryDTO dto = new DashboardSummaryDTO();
        dto.setTotalIncome(income);
        dto.setTotalExpense(expense);
        dto.setBalance(income - expense);

        return dto;
    }

    @Override
    public Map<String, Double> getCategoryBreakdown(Long userId) {

        List<FinancialRecord> records = financialRecordRepository.findByUserId(userId);

        Map<String, Double> breakdown = new HashMap<>();

        for (FinancialRecord record : records) {
            String category = record.getCategory();
            double amount = record.getAmount();

            breakdown.put(category, breakdown.getOrDefault(category, 0.0) + amount);
        }

        return breakdown;
    }
}