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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
        } catch (NoSuchElementException noSuchElementException) {
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

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        userService.saveUser(user);
        return "signUp";
    }

    @GetMapping("/addFriend")
    public String getAddFriend(Model model) {
        model.addAttribute("user", new User());
        return "contactForm";
    }

    @PostMapping("/addFriend")
    public String postAddFriend(@ModelAttribute User user,
                                Principal principal) {

        String friendEmail = user.getEmail();
        User friend = userService.getUserByEmail(friendEmail).orElse(null);
        User connectedUser = userService.getUserByEmail(principal.getName()).get();

        if (friend != null) {
            connectedUser.addFriend(friend);
            userService.saveUser(connectedUser);
        }
        return "templateApp.html";
    }

    @GetMapping("/templateApp.html")
    public String template() {
        return "templateApp.html";
    }

    @GetMapping("/contactForm")
    public String getContactForm(Model model) {
        return "contactForm.html";
    }

    @GetMapping("/signUp.html")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "signUp.html";
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

    @GetMapping("/contacts")
    public String showContacts(Model model, Principal principal) {
        String username = principal.getName();
        User connectedUser = userService.getUserByEmail(username).get();

        List<User> friends = userService.getAllFriend(connectedUser);

        model.addAttribute("friends", friends);
        return "contacts";
    }
}
