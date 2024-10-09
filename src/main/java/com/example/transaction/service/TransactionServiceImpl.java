package com.example.transaction.service;

import com.example.transaction.exception.RecordNotFound;
import com.example.transaction.dto.TransactionResponse;
import com.example.transaction.entity.Transactions;
import com.example.transaction.util.DataSetUp;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{


    public List<TransactionResponse> getTransactionsByCategory(String category) throws Exception {
        List<Transactions>  transactionsList=DataSetUp.transactionsList;
        List<TransactionResponse> responseList = transactionsList.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .map(t1 -> createTransactionResponse(t1))
                .sorted((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()))
                .collect(Collectors.toList());
            if(responseList.isEmpty())
            {
                throw new RecordNotFound();
            }
           return responseList;
    }

    private TransactionResponse createTransactionResponse(Transactions transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionDate(convertToDate(transaction.getTransactionDate()));
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setCategory(transaction.getCategory());
        transactionResponse.setType(transaction.getType());
        transactionResponse.setVendor(transaction.getVendor());
        return transactionResponse;
    }

    public List<TransactionResponse> getAverageForCategory(String category){
        Map<String,List<Transactions>> transMap =DataSetUp.transactionsList.stream()
                .filter(t->t.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.groupingBy(t->monthYear(t)));
        List<TransactionResponse> transactionResponse= new ArrayList<>();
        if(transMap.isEmpty()){
              throw new RecordNotFound();
        }
        for (Map.Entry<String,List<Transactions>> entry:transMap.entrySet()) {
            TransactionResponse response= new TransactionResponse();
            BigDecimal total=entry.getValue().stream()
                    .map(t->t.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
            response.setMonthYear(entry.getKey());
            long count = entry.getValue().stream().count();
            BigDecimal average= total.divide(BigDecimal.valueOf(count),2,RoundingMode.HALF_UP);
            response.setAverage(average);
            response.setCategory(category);
            transactionResponse.add(response);
        }

        return transactionResponse;
    }

    private String monthYear(Transactions transactions) {
        LocalDate date= convertToDate(transactions.getTransactionDate());
        return date.getMonth()+"-"+date.getYear();
    }

    public TransactionResponse getHighestForCategory(String category,int year){

        Optional<BigDecimal> highest =DataSetUp.transactionsList.stream()
                .filter(t->t.getCategory().equalsIgnoreCase(category))
                .filter(t->convertToDate(t.getTransactionDate()).getYear()==year)
                .map(t->t.getAmount())
                .max(Comparator.naturalOrder());
        if(highest.isEmpty())
        {
            throw new RecordNotFound();
        }
        TransactionResponse transactionResponse= new TransactionResponse();
        transactionResponse.setHighest(highest.get());
        transactionResponse.setCategory(category);
        return transactionResponse;
    }
    public TransactionResponse getLowestForCategory(String category,int year){
        Optional<BigDecimal>  lowest =DataSetUp.transactionsList.stream()
                .filter(t->t.getCategory().equalsIgnoreCase(category))
                .filter(t->convertToDate(t.getTransactionDate()).getYear()==year)
                .map(t->t.getAmount())
                .min(Comparator.naturalOrder());
        TransactionResponse transactionResponse= new TransactionResponse();
        if(lowest.isEmpty())
        {
            throw new RecordNotFound();
        }
        transactionResponse.setLowest(lowest.get());
        transactionResponse.setCategory(category);
        return transactionResponse;
    }
    public List<TransactionResponse> getTotalByCategory(){

        List<TransactionResponse> transactionResponse= new ArrayList<>();

        Map<String, List<Transactions>> transMap = DataSetUp.transactionsList.stream()
                .collect(Collectors.groupingBy(Transactions::getCategory));

        for (Map.Entry<String,List<Transactions>> entry:transMap.entrySet()) {
            TransactionResponse response= new TransactionResponse();
            response.setTotal(entry.getValue().stream()
                    .map(t->t.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
            response.setCategory(entry.getKey());
            transactionResponse.add(response);

        }
        return transactionResponse;
    }

    private LocalDate convertToDate(String date)  {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");

            return LocalDate.parse(date,formatter);
        }catch (Exception ex)
        {
            throw new RuntimeException("Parse exception");
        }
    }


}
