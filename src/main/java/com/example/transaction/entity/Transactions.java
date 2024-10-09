package com.example.transaction.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
public class Transactions {

   private String transactionDate;
   private String type;
   private String vendor;
   private BigDecimal amount;
   private String category;



}
