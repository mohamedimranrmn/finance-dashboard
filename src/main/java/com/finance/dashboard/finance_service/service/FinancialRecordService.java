package com.finance.dashboard.finance_service.service;

import com.finance.dashboard.finance_service.dto.FinancialRecordRequestDTO;
import com.finance.dashboard.finance_service.dto.FinancialRecordResponseDTO;

import java.util.List;

public interface FinancialRecordService {

    FinancialRecordResponseDTO createRecord(FinancialRecordRequestDTO request);

    List<FinancialRecordResponseDTO> getAllRecords();

    List<FinancialRecordResponseDTO> getRecordsByUser(Long userId);
    
    void deleteRecordsByUser(Long userId);
    
    void deleteRecordById(Long id);
    
    double calculateTotalIncome(Long userId);

    double calculateTotalExpense(Long userId);
    
    FinancialRecordResponseDTO updateRecord(Long id, FinancialRecordRequestDTO request);
    
    List<FinancialRecordResponseDTO> getByType(String type);

    List<FinancialRecordResponseDTO> getByCategory(String category);

    List<FinancialRecordResponseDTO> getByDateRange(String startDate, String endDate);
}