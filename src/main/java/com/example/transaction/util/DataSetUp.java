package com.example.transaction.util;

import com.example.transaction.entity.Transactions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSetUp {

    public static List<Transactions> transactionsList= new ArrayList<>();

    static{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        TypeReference<List<Transactions>> jacksonTypeReference = new TypeReference<>() {
        };

        try {
             transactionsList = mapper.readValue(new File("src/main/resources/transactions.json"),jacksonTypeReference);
            transactionsList.stream().filter(transactions -> transactions.getCategory() == null)
                    .forEach(transactions -> transactions.setCategory("Uncategorized"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
