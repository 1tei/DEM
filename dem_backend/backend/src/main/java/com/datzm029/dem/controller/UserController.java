package com.datzm029.dem.controller;

import com.datzm029.dem.Services.UserService;
import com.datzm029.dem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/add/user")
    public void createUser(@RequestBody User user){
        System.out.println("Called createUser");
        userService.addUser(new User(UUID.randomUUID(),
                user.getUsername(),
                user.getName(),
                user.getRegion(),
                user.getAddress(),
                user.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                user.getPassword())
        );
    }
}
