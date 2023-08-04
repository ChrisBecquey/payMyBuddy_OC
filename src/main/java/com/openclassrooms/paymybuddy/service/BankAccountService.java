package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.constant.TransactionType;
import com.openclassrooms.paymybuddy.exception.LowBalanceException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Transactional
public class BankAccountService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void makeBankTransaction(String email, Double amount, TransactionType transactionType, String description, String iban) throws LowBalanceException {


        BankAccount bankTransaction = new BankAccount();
        Double totalAmount = 0.0;

        User userSender = userRepository.findUserByEmail(email).orElseThrow(
                () -> new NoSuchElementException("Thi user " + email + "doesn't exist in database"));
        Double balance = userSender.getBalance();
        //taxe de 0.5% à chaque transaction => peut etre faire une classe commune pour toutes les transactions avec cette partie
        Double tax = amount * 0.005;

        switch (transactionType) {
            case CREDIT: {
                balance = balance + amount;
                userSender.setBalance(balance);
                break;
            }
            case DEBIT: {
                if (balance < amount) {
                    throw new LowBalanceException("Sorry your solde " + balance + "is not hight enought to make this transaction");
                }
                totalAmount = amount + tax;
                balance = balance - totalAmount;
                userSender.setBalance(balance);
                break;
            }
            default:
                throw new IllegalArgumentException("Unknow TransactionType");
        }

        bankTransaction.setAmount(amount);
        bankTransaction.setUserId(userSender);
        bankTransaction.setDescription(description);
        bankTransaction.setIban(iban);
        bankTransaction.setType(transactionType.name());

        bankAccountRepository.save(bankTransaction);
    }
}
