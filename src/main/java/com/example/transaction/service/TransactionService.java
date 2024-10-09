package com.example.transaction.service;

import com.example.transaction.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {
    public List<TransactionResponse> getTransactionsByCategory(String category) throws Exception;
    public List<TransactionResponse> getAverageForCategory(String category);
    public TransactionResponse getHighestForCategory(String category,int year);
    public TransactionResponse getLowestForCategory(String category,int year);
    public List<TransactionResponse> getTotalByCategory();
}
