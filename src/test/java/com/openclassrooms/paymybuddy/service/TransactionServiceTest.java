package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class TransactionServiceTest {
    @InjectMocks
    TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;


    @Test
    void getTransactionsByUser() {
        User user = new User();
        user.setId(1);
        user.setEmail("jhon@gmail.com");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        transactions.add(new Transaction());

        when(transactionRepository.findByConnectionId(user)).thenReturn(transactions);

        List<Transaction> result = transactionService.getTransactionsByUser(user);

        verify(transactionRepository, times(1)).findByConnectionId(user);
        assertEquals(transactions.size(), result.size());
    }
}