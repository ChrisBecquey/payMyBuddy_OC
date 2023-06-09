package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String description;
    private Double amount;
    private LocalDate transactionDate;
    @ManyToOne(cascade = CascadeType.MERGE)
    private User connectionId;
    @ManyToOne(cascade = CascadeType.MERGE)
    private User to_user_id;

    public Transaction() {
    }

    public Transaction(String description, Double amount,LocalDate transactionDate,  User connectionId, User to_user_id) {
        this.description = description;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.connectionId = connectionId;
        this.to_user_id = to_user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(User connectionId) {
        this.connectionId = connectionId;
    }

    public User getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(User to_user_id) {
        this.to_user_id = to_user_id;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
