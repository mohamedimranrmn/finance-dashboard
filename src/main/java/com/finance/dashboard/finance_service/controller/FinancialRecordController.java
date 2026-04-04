package com.finance.dashboard.finance_service.controller;

import com.finance.dashboard.finance_service.dto.FinancialRecordRequestDTO;
import com.finance.dashboard.finance_service.dto.FinancialRecordResponseDTO;
import com.finance.dashboard.finance_service.service.FinancialRecordService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class FinancialRecordController {

    private final FinancialRecordService financialRecordService;

    public FinancialRecordController(FinancialRecordService financialRecordService) {
        this.financialRecordService = financialRecordService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public FinancialRecordResponseDTO createRecord(@RequestBody FinancialRecordRequestDTO request) {
        return financialRecordService.createRecord(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public List<FinancialRecordResponseDTO> getAllRecords() {
        return financialRecordService.getAllRecords();
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public List<FinancialRecordResponseDTO> getRecordsByUser(@PathVariable Long userId) {
        return financialRecordService.getRecordsByUser(userId);
    }
    
    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRecordsByUser(@PathVariable Long userId) {
        financialRecordService.deleteRecordsByUser(userId);
        return "All records deleted for userId: " + userId;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRecord(@PathVariable Long id) {
        financialRecordService.deleteRecordById(id);
        return "Record deleted successfully with id: " + id;
    }
    
    @GetMapping("/income/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public double getTotalIncome(@PathVariable Long userId) {
        return financialRecordService.calculateTotalIncome(userId);
    }

    @GetMapping("/expense/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public double getTotalExpense(@PathVariable Long userId) {
        return financialRecordService.calculateTotalExpense(userId);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public FinancialRecordResponseDTO updateRecord(@PathVariable Long id, @RequestBody FinancialRecordRequestDTO request) {
        return financialRecordService.updateRecord(id, request);
    }
    
    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public List<FinancialRecordResponseDTO> getByType(@PathVariable String type) {
        return financialRecordService.getByType(type);
    }
    
    @GetMapping("/category/{category}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public List<FinancialRecordResponseDTO> getByCategory(@PathVariable String category) {
        return financialRecordService.getByCategory(category);
    }
    
    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public List<FinancialRecordResponseDTO> getByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        return financialRecordService.getByDateRange(startDate, endDate);
    }
    
}