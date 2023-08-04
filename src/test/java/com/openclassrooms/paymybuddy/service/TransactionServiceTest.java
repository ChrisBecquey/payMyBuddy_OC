package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.LowBalanceException;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class TransactionServiceTest {
    @InjectMocks
    TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;
    @Mock
    UserRepository userRepository;


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

    @Test
    void shouldMakeTheTransaction_whenBalanceIsOk() {
        User user1 = new User();
        User user2 = new User();

        user1.setBalance(100.0);
        user2.setBalance(85.0);

        try {
            transactionService.makeTransaction(user1, user2, 50.0, "Payment");

            assertEquals(49.75, user1.getBalance());
            assertEquals(135.0, user2.getBalance());

        } catch (LowBalanceException e) {
            Assertions.fail("Unexpected LowBalanceException");
        }


    }

    @Test
    void shouldNotMakeTheTransaction_whenBalanceIsLowerThanAmount() {
        User user1 = new User();
        User user2 = new User();

        user1.setBalance(100.0);
        user2.setBalance(50.0);

        try {
            transactionService.makeTransaction(user1, user2, 150.0, "Payment");
            Assertions.fail("Expected LowBalanceException");
        } catch (LowBalanceException e) {
            Assertions.assertEquals("Sorry your solde 100.0 is not enought, you can't make this transaction !", e.getMessage());
        }
    }

    @Test
    void shouldSaveTransaction() throws LowBalanceException {
        User user1 = new User();
        User user2 = new User();

        user1.setBalance(100.0);
        user2.setBalance(50.0);

        transactionService.makeTransaction(user1, user2, 50.0, "Payment");

        verify(userRepository, Mockito.times(1)).save(user1);
        verify(userRepository, Mockito.times(1)).save(user2);
        verify(transactionRepository, Mockito.times(1)).save(any());
    }
}