package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.LowBalanceException;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Transaction> getTransactionsByUser(User user) {
        return transactionRepository.findByConnectionId(user);
    }

    public void makeTransaction(User connectedUser, User friend, Double amount, String description) throws LowBalanceException {
        Transaction transaction = new Transaction();
        Double senderBalance = connectedUser.getBalance();
        Double receiverBalance = friend.getBalance();

        if(senderBalance < amount){
            throw new LowBalanceException("Sorry your solde " + senderBalance + " is not enought, you can't make this transaction !");
        }

        Double tax = amount * 0.005;
        Double totalAmount = amount + tax;

        senderBalance = senderBalance - totalAmount;
        connectedUser.setBalance(senderBalance);

        receiverBalance = receiverBalance + amount;
        friend.setBalance(receiverBalance);

        userRepository.save(connectedUser);
        userRepository.save(friend);

        transaction.setDescription(description);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setAmount(amount);
        transaction.setConnectionId(connectedUser);
        transaction.setTo_user_id(friend);

        transactionRepository.save(transaction);
    }

    public Page<Transaction> findPaginated(Pageable pageable, User user) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Transaction> transactionList = getTransactionsByUser(user);
        List<Transaction> list;
        if (transactionList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, transactionList.size());
            list = transactionList.subList(startItem, toIndex);
        }

        Page<Transaction> transactionPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), transactionList.size());

        return  transactionPage;
    }
}
