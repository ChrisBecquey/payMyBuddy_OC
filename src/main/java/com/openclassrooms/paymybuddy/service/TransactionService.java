package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    //méthode pour Lister les transaction effectué
    public List<Transaction> getTransactionsByUser(User user) {
        return transactionRepository.findByConnectionId(user);
    }

    //Méthode pour sélectionner une personne + définir le montant a envoyé

}
