package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
public class WebController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @GetMapping("/user/transaction")
    public String getTransactionForUser(Model model, @RequestParam User user) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByUser(user);
            model.addAttribute("listTransactions", transactions);
        }catch (NoSuchElementException noSuchElementException) {
    log.error(noSuchElementException.getMessage(), noSuchElementException);
        }
        return "transaction";
    }

    @GetMapping("/transactions")
    public String getTransactionsForUser(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email).get();
        List<Transaction> transactions = transactionService.getTransactionsByUser(user);
        model.addAttribute("transactions", transactions);
        return "transaction";
    }

    @GetMapping("/templateApp.html")
    public String template() {
        return "templateApp.html";
    }

    // Login form
    @RequestMapping("/login.html")
    public String login() {
        return "login.html";
    }

    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }
}
