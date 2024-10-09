package com.example.transaction.controller;

import com.example.transaction.exception.GlobalExceptionHandler;
import com.example.transaction.exception.RecordNotFound;
import com.example.transaction.service.TransactionServiceImpl;
import com.example.transaction.util.AbstractClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    private MockMvc mvc;
    @InjectMocks
    TransactionController transactionController;
    @Mock
    private TransactionServiceImpl transactionService;
    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    @Test
    void validateGetTransaction() throws Exception {
        when(transactionService.getTransactionsByCategory(anyString())).thenReturn(AbstractClass.getTransactionResponse("alltransactions"));
        MvcResult mvcResult= mvc.perform(MockMvcRequestBuilders.get("/transaction/getTransactions")
        .param("category","test"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("[{\"category\":\"test\",\"transactionDate\":\"01-Jul-2024\",\"type\":\"card\",\"vendor\":\"M&S\",\"amount\":\"11.2\"}]",mvcResult.getResponse().getContentAsString());

    }
    @Test
    void validatingTransactionNotFound() throws Exception {
        when(transactionService.getTransactionsByCategory(anyString())).thenThrow(new RecordNotFound());
        mvc.perform(MockMvcRequestBuilders.get("/transaction/getTransactions")
                .param("category","test"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"errorMessage\":\"Record Not found\"}"));

    }
    @Test
    void validateGetAverageByCategory() throws Exception {
        when(transactionService.getAverageForCategory(anyString())).thenReturn(AbstractClass.getTransactionResponse("average"));
        MvcResult mvcResult= mvc.perform(MockMvcRequestBuilders.get("/transaction/getAverageByCategory")
                .param("category","test"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("[{\"category\":\"test\",\"monthYear\":\"JULY-2024\",\"average\":\"11.2\"}]",mvcResult.getResponse().getContentAsString());

    }
    @Test
    void validateGetAverageByCategoryNotFound() throws Exception {
        when(transactionService.getAverageForCategory(anyString())).thenThrow(new RecordNotFound());
        mvc.perform(MockMvcRequestBuilders.get("/transaction/getAverageByCategory")
                .param("category","test"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"errorMessage\":\"Record Not found\"}"));

    }
  @Test
    void validategetHighestAmountByCategory() throws Exception {
        when(transactionService.getHighestForCategory(anyString(),anyInt())).thenReturn(AbstractClass.getTransactionResponse("highestamount").get(0));
        MvcResult mvcResult= mvc.perform(MockMvcRequestBuilders.get("/transaction/getHighestAmountByCategory")
                .param("category","test")
        .param("year", String.valueOf(2023)))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("{\"category\":\"test\",\"highest\":\"15.2\"}",mvcResult.getResponse().getContentAsString());

    }
    @Test
    void validategetLowestAmountByCategory() throws Exception {
        when(transactionService.getLowestForCategory(anyString(),anyInt())).thenReturn(AbstractClass.getTransactionResponse("lowestamount").get(0));
        MvcResult mvcResult= mvc.perform(MockMvcRequestBuilders.get("/transaction/getLowestAmountByCategory")
                .param("category","test")
                .param("year", String.valueOf(2023)))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("{\"category\":\"test\",\"lowest\":\"1.22\"}",mvcResult.getResponse().getContentAsString());

    }
    @Test
    void validateGetTotalTransaction() throws Exception {
        when(transactionService.getTotalByCategory()).thenReturn(AbstractClass.getTransactionResponse("total"));
        MvcResult mvcResult= mvc.perform(MockMvcRequestBuilders.get("/transaction/getTotalAmountByCategory"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("[{\"category\":\"test\",\"total\":\"10.56\"}]",mvcResult.getResponse().getContentAsString());

    }


}
