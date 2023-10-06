package com.openclassrooms.paymybuddy;

import com.openclassrooms.paymybuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.openclassrooms.paymybuddy.service.UserService;

import java.util.Optional;

@SpringBootApplication
public class PaymybuddyApplication{

    public static void main(String[] args) {
        SpringApplication.run(PaymybuddyApplication.class, args);
    }
}
