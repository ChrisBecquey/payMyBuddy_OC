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

/*
    @Autowired
    private UserService userService;
*/

    public static void main(String[] args) {
        SpringApplication.run(PaymybuddyApplication.class, args);
    }

/*    @Override
    public void run(String... args) throws Exception {
        User userTest = new User("Jean", "Boyd", "jean.boyd@gmail.com", "password", 12D);
        userService.saveUser(userTest);

        Iterable<User> users = userService.getUsers();
        users.forEach(user -> System.out.println(user.getFirstName()));

        Optional<User> optUser = userService.getUserById(1);
        User user1 = optUser.get();
        System.out.println(user1.getFirstName());
    }*/
}
