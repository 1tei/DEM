package com.datzm029.dem.controller;

import com.datzm029.dem.Services.UserService;
import com.datzm029.dem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
                user.getEmail(),
                0,
                BigInteger.ZERO,
                BigInteger.ZERO,
                BigInteger.ZERO,
                BigInteger.ZERO,
                user.getPassword())
        );
    }

    @GetMapping(value = "/getUser")
    public User getUserById(@RequestParam("userId") String userId){
        System.out.println("Called getUser by id" + userId);
        return userService.selectUser(UUID.fromString(userId));
    }

    @GetMapping(value = "/loginUser")
    public UUID getLoggedUser(@RequestParam("login") String login){
        System.out.println("Called getUser");
        return userService.getId(login);
    }
}
