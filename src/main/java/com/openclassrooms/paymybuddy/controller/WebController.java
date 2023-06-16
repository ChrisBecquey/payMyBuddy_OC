package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;


@Controller
public class WebController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String getUser(@RequestParam(name = "firstName", required = false, defaultValue = "Pauline") String firstName, Model model) {
        model.addAttribute("firstName", firstName);
        return "user";
    }

    @GetMapping("/greeting")
    public String test(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "test";
    }

    @GetMapping("/transactions")
    public String getTransactionsForUser(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email).get();
        List<Transaction> transactions = transactionService.getTransactionsByUser(user);
        model.addAttribute("transactions", transactions);
        return "transaction";

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
