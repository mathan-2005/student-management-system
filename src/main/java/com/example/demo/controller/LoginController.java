package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")

public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/login")
    public String login(@RequestBody User user){

        User validUser =
            userRepo.findByUsernameAndPassword(
                user.getUsername(),
                user.getPassword()
            );

        if(validUser != null){

            return "success";
        }

        return "fail";
    }
}