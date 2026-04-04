package com.finance.dashboard.finance_service.service.impl;

import com.finance.dashboard.finance_service.dto.FinancialRecordRequestDTO;
import com.finance.dashboard.finance_service.dto.FinancialRecordResponseDTO;
import com.finance.dashboard.finance_service.entity.FinancialRecord;
import com.finance.dashboard.finance_service.entity.TransactionType;
import com.finance.dashboard.finance_service.repository.FinancialRecordRepository;
import com.finance.dashboard.finance_service.service.FinancialRecordService;
import com.finance.dashboard.user_service.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialRecordServiceImpl implements FinancialRecordService {
	
	private final UserRepository userRepository;

    private final FinancialRecordRepository financialRecordRepository;

    public FinancialRecordServiceImpl(FinancialRecordRepository financialRecordRepository, UserRepository userRepository) {
		this.financialRecordRepository = financialRecordRepository;
		this.userRepository = userRepository;
}

    @Override
    public FinancialRecordResponseDTO createRecord(FinancialRecordRequestDTO request) {
        if (request.getUserId() == null) {
            throw new RuntimeException("UserId must not be null");
        }
        if (!userRepository.existsById(request.getUserId())) {
            throw new RuntimeException("User does not exist with id: " + request.getUserId());
        }
        if (request.getAmount() <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }
        if (request.getType() == null) {
            throw new RuntimeException("Transaction type is required");
        }
        if (request.getDate() == null) {
            throw new RuntimeException("Date is required");
        }
        FinancialRecord record = new FinancialRecord();
        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setDescription(request.getDescription());
        record.setUserId(request.getUserId());
        FinancialRecord saved = financialRecordRepository.save(record);
        return mapToDTO(saved);
    }

    @Override
    public List<FinancialRecordResponseDTO> getAllRecords() {
        return financialRecordRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FinancialRecordResponseDTO> getRecordsByUser(Long userId) {
        return financialRecordRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    
    @Override
    public void deleteRecordsByUser(Long userId) {
        List<FinancialRecord> records = financialRecordRepository.findByUserId(userId);
        if (records.isEmpty()) {
            throw new RuntimeException("No records found for userId: " + userId);
        }
        financialRecordRepository.deleteAll(records);
    }
    
    @Override
    public void deleteRecordById(Long id) {
        if (!financialRecordRepository.existsById(id)) {
            throw new RuntimeException("Record not found with id: " + id);
        }
        financialRecordRepository.deleteById(id);
    }
    
    public double calculateTotalIncome(Long userId) {
        return financialRecordRepository.findByUserId(userId)
                .stream()
                .filter(r -> r.getType() == TransactionType.INCOME)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }
    
    public double calculateTotalExpense(Long userId) {
        return financialRecordRepository.findByUserId(userId)
                .stream()
                .filter(r -> r.getType() == TransactionType.EXPENSE)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    private FinancialRecordResponseDTO mapToDTO(FinancialRecord record) {
        FinancialRecordResponseDTO dto = new FinancialRecordResponseDTO();
        dto.setId(record.getId());
        dto.setAmount(record.getAmount());
        dto.setType(record.getType());
        dto.setCategory(record.getCategory());
        dto.setDate(record.getDate());
        dto.setDescription(record.getDescription());
        dto.setUserId(record.getUserId());
        return dto;
    }
    
    @Override
    public FinancialRecordResponseDTO updateRecord(Long id, FinancialRecordRequestDTO request) {
        FinancialRecord record = financialRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found with id: " + id));
        if (request.getAmount() != 0) {
            record.setAmount(request.getAmount());
        }
        if (request.getType() != null) {
            record.setType(request.getType());
        }
        if (request.getCategory() != null) {
            record.setCategory(request.getCategory());
        }
        if (request.getDate() != null) {
            record.setDate(request.getDate());
        }
        if (request.getDescription() != null) {
            record.setDescription(request.getDescription());
        }
        if (request.getUserId() != null &&
            !request.getUserId().equals(record.getUserId())) {
            if (!userRepository.existsById(request.getUserId())) {
                throw new RuntimeException("User does not exist with id: " + request.getUserId());
            }
            record.setUserId(request.getUserId());
        }

        FinancialRecord updated = financialRecordRepository.save(record);

        return mapToDTO(updated);
    }
    
    @Override
    public List<FinancialRecordResponseDTO> getByType(String type) {
        return financialRecordRepository.findByType(
                com.finance.dashboard.finance_service.entity.TransactionType.valueOf(type))
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<FinancialRecordResponseDTO> getByCategory(String category) {
        return financialRecordRepository.findByCategory(category)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<FinancialRecordResponseDTO> getByDateRange(String startDate, String endDate) {
        java.time.LocalDate start = java.time.LocalDate.parse(startDate);
        java.time.LocalDate end = java.time.LocalDate.parse(endDate);
        return financialRecordRepository.findByDateBetween(start, end)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    
}