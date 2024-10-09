package com.example.transaction.util;

import com.example.transaction.dto.TransactionResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AbstractClass {
    public static List<TransactionResponse> getTransactionResponse(String type) {

        List<TransactionResponse> responseList=new ArrayList<TransactionResponse>();
        TransactionResponse response= new TransactionResponse();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");

        response.setCategory("test");
        if(type.equalsIgnoreCase("alltransactions"))
        {
            response.setTransactionDate(LocalDate.parse("01/Jul/2024",formatter));
            response.setType("card");
            response.setVendor("M&S");
            response.setAmount(BigDecimal.valueOf(11.20));
        }
        else if(type.equalsIgnoreCase("average"))
        {
            response.setMonthYear("JULY-2024");
            response.setAverage(BigDecimal.valueOf(11.20));
        }
        else if(type.equalsIgnoreCase("highestamount"))
        {
            response.setHighest(BigDecimal.valueOf(15.20));
        }
        else if(type.equalsIgnoreCase("lowestamount"))
        {
            response.setLowest(BigDecimal.valueOf(1.22));
        }
        else if(type.equalsIgnoreCase("total"))
        {
            response.setTotal(BigDecimal.valueOf(10.56));
        }
        responseList.add(response);
        return  responseList;
    }

}
