package com.example.transaction.service;

import com.example.transaction.dto.TransactionResponse;
import com.example.transaction.exception.RecordNotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void validateGetTransactionsByCategory() throws Exception {
        List<TransactionResponse> response = transactionService.getTransactionsByCategory("Utility");
        assertEquals("Utility", response.get(0).getCategory());
        assertEquals(BigDecimal.valueOf(10.56),response.get(0).getAmount());
    }
    @Test
    void validateGetTransactionsException() throws Exception {
        assertThrows(RecordNotFound.class,()->
        {
            transactionService.getTransactionsByCategory("test");
        });
    }
    @Test
    void validateGetAverageForCategory() throws Exception {
        List<TransactionResponse> response = transactionService.getAverageForCategory("Utility");
        assertEquals("Utility", response.get(0).getCategory());
        assertEquals(BigDecimal.valueOf(10.56),response.get(0).getAverage());
    }
    @Test
    void validateGetAverageForCategoryNotfound() throws Exception {
        assertThrows(RecordNotFound.class,()->
        {
            transactionService.getAverageForCategory("test");
        });
    }
    @Test
    void validategetHighestForCategory() throws Exception {
        TransactionResponse response = transactionService.getHighestForCategory("Utility",2023);
        assertEquals("Utility", response.getCategory());
        assertEquals(BigDecimal.valueOf(10.56),response.getHighest());
    }
    @Test
    void validategetLowestForCategory() throws Exception {
        TransactionResponse response = transactionService.getLowestForCategory("Utility",2023);
        assertEquals("Utility", response.getCategory());
        assertEquals(BigDecimal.valueOf(10.56),response.getLowest());
    }
    @Test
    void validategetTotalByCategory() throws Exception {
        List<TransactionResponse>  response = transactionService.getTotalByCategory();
        assertEquals("Utility", response.get(0).getCategory());
        assertEquals(BigDecimal.valueOf(10.56),response.get(0).getTotal());
    }
}
