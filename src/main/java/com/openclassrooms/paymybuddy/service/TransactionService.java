package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.LowBalanceException;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Transaction> getTransactionsByUser(User user) {
        return transactionRepository.findByConnectionId(user);
    }

    //Méthode pour sélectionner une personne + définir le montant a envoyé

    public void makeTransaction(User connectedUser, User friend, Double amount, String description) throws LowBalanceException {
        Transaction transaction = new Transaction();
        Double senderBalance = connectedUser.getBalance();
        Double receiverBalance = friend.getBalance();

        // appliquer la transactions (règles entre si balance >= amount)
        if(senderBalance < amount){
            throw new LowBalanceException("Sorry your solde " + senderBalance + " is not enought, you can't make this transaction !");
        }

        Double tax = amount * 0.005;
        Double totalAmount = amount + tax;

        senderBalance = senderBalance - totalAmount;
        connectedUser.setBalance(senderBalance);

        receiverBalance = receiverBalance + amount;
        friend.setBalance(receiverBalance);

        // save les changement sur les 2 utilisateurs

        userRepository.save(connectedUser);
        userRepository.save(friend);


        //Save la transaction pour pouvoir la retrouver plus tard
        transaction.setDescription(description);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setAmount(amount);
        transaction.setConnectionId(connectedUser);
        transaction.setTo_user_id(friend);

        transactionRepository.save(transaction);
    }
}
