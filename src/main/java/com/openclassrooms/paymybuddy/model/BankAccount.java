package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String iban;
    private String description;
    private String type;
    private Double amount;
    private User userId;


    public BankAccount() {
    }

    public BankAccount(String iban, String description, String type, Double amount, User userId) {
        this.iban = iban;
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.userId = userId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

}