package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.constant.TransactionType;
import com.openclassrooms.paymybuddy.exception.LowBalanceException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BankAccountServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BankAccountRepository bankAccountRepository;
    @InjectMocks
    private BankAccountService bankAccountService;

    @Test
    void shouldCreditYourBankAccount_whenEveryInformationAreOk() throws LowBalanceException {
        User user1 = new User();
        user1.setEmail("user1@gmail.com");
        user1.setFirstName("John");
        user1.setLastName("Boyd");
        user1.setBalance(0.00);
        user1.setPassword("password");

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(1);
        bankAccount1.setType("CREDIT");
        bankAccount1.setDescription("Rent");
        bankAccount1.setAmount(100.00);
        bankAccount1.setIban("FR7630001007941234567890185");
        bankAccount1.setUserId(user1);

        when(userRepository.findUserByEmail("user1@gmail.com")).thenReturn(Optional.of(user1));

        bankAccountService.makeBankTransaction("user1@gmail.com", 100.00, TransactionType.CREDIT, "Rent", "FR7630001007941234567890185");
        verify(userRepository, Mockito.times(1)).findUserByEmail("user1@gmail.com");
        //verify(bankAccountRepository, Mockito.times(1)).save(bankAccount1);
    }

    @Test
    void shouldNotSaveTransaction_whenBalanceIsLowerThanTransferAmount() throws LowBalanceException {
        User user1 = new User();
        user1.setEmail("user1@gmail.com");
        user1.setFirstName("John");
        user1.setLastName("Boyd");
        user1.setBalance(50.00);
        user1.setPassword("password");

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(1);
        bankAccount1.setType("DEBIT");
        bankAccount1.setDescription("Rent");
        bankAccount1.setAmount(100.00);
        bankAccount1.setIban("FR7630001007941234567890185");
        bankAccount1.setUserId(user1);

        when(userRepository.findUserByEmail("user1@gmail.com")).thenReturn(Optional.of(user1));

        assertThrows(LowBalanceException.class,()->bankAccountService.makeBankTransaction("user1@gmail.com", 100.00, TransactionType.DEBIT, "Rent", "FR7630001007941234567890185" ));
        verify(userRepository, Mockito.times(1)).findUserByEmail("user1@gmail.com");
        verify(bankAccountRepository, Mockito.times(0)).save(bankAccount1);
    }

    @Test
    void shouldMakeTheDebit_whenBalanceIsGreaterThanTransferAmount() throws LowBalanceException {
        User user1 = new User();
        user1.setEmail("user1@gmail.com");
        user1.setFirstName("John");
        user1.setLastName("Boyd");
        user1.setBalance(200.00);
        user1.setPassword("password");

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(1);
        bankAccount1.setType("DEBIT");
        bankAccount1.setDescription("Rent");
        bankAccount1.setAmount(100.00);
        bankAccount1.setIban("FR7630001007941234567890185");
        bankAccount1.setUserId(user1);

        when(userRepository.findUserByEmail("user1@gmail.com")).thenReturn(Optional.of(user1));

        bankAccountService.makeBankTransaction("user1@gmail.com", 100.00, TransactionType.DEBIT, "Rent", "FR7630001007941234567890185");

        verify(userRepository, Mockito.times(1)).findUserByEmail("user1@gmail.com");
    }
}