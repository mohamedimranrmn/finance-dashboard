package com.finance.dashboard.finance_service.repository;

import com.finance.dashboard.finance_service.entity.FinancialRecord;
import com.finance.dashboard.finance_service.entity.TransactionType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByUserId(Long userId);

    List<FinancialRecord> findByType(TransactionType type);
    
    List<FinancialRecord> findByCategory(String category);

    List<FinancialRecord> findByDateBetween(LocalDate start, LocalDate end);

    List<FinancialRecord> findByUserIdAndType(Long userId, TransactionType type);
    
    void deleteByUserId(Long userId);
    
    void deleteRecordById(Long Id);
    
}