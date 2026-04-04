package com.finance.dashboard.dashboard_service.controller;

import com.finance.dashboard.dashboard_service.dto.DashboardSummaryDTO;
import com.finance.dashboard.dashboard_service.service.DashboardService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public DashboardSummaryDTO getSummary(@PathVariable Long userId) {
        return dashboardService.getSummary(userId);
    }

    @GetMapping("/category/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public Map<String, Double> getCategoryBreakdown(@PathVariable Long userId) {
        return dashboardService.getCategoryBreakdown(userId);
    }
}