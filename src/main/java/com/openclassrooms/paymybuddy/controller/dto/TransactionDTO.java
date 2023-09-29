package com.openclassrooms.paymybuddy.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Double amount;
    private String emailReceiver;
    private String description;
}
