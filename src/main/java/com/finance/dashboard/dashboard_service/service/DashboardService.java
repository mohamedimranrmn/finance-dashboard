package com.finance.dashboard.dashboard_service.service;

import com.finance.dashboard.dashboard_service.dto.DashboardSummaryDTO;

import java.util.Map;

public interface DashboardService {

    DashboardSummaryDTO getSummary(Long userId);

    Map<String, Double> getCategoryBreakdown(Long userId);
}