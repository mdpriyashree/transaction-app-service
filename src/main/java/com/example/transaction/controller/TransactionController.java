package com.example.transaction.controller;

import com.example.transaction.dto.TransactionResponse;
import com.example.transaction.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionServiceImpl transactionServiceImpl;

    @GetMapping("/getTransactions")
    public ResponseEntity<TransactionResponse> getTransaction(@RequestParam String category) throws Exception {
        return new ResponseEntity(transactionServiceImpl.getTransactionsByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/getAverageByCategory")
    public ResponseEntity<TransactionResponse> getAverageByCategory(@RequestParam String category) {
        return new ResponseEntity(transactionServiceImpl.getAverageForCategory(category), HttpStatus.OK);
    }

    @GetMapping("/getHighestAmountByCategory")
    public ResponseEntity<TransactionResponse> getHighestAmountByCategory(@RequestParam String category, @RequestParam @Min(2000) @Max(2099) int year) {
        return new ResponseEntity(transactionServiceImpl.getHighestForCategory(category, year), HttpStatus.OK);
    }

    @GetMapping("/getLowestAmountByCategory")
    public ResponseEntity<TransactionResponse> getLowestAmountByCategory(@RequestParam String category,
                                                                         @RequestParam @Min(2000) @Max(2099) int year) {
        return new ResponseEntity(transactionServiceImpl.getLowestForCategory(category, year), HttpStatus.OK);
    }

    @GetMapping("/getTotalAmountByCategory")
    public ResponseEntity<TransactionResponse> getTotalAmountByCategory() {
        return new ResponseEntity(transactionServiceImpl.getTotalByCategory(), HttpStatus.OK);
    }
}
