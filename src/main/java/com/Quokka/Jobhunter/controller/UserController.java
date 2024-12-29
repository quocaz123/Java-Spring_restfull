package com.Quokka.Jobhunter.controller;

import com.Quokka.Jobhunter.domain.User;
import com.Quokka.Jobhunter.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String createNewUser(){
        User user = new User();
        user.setEmail("quocthangbinh234@gmail.com");
        user.setName("Quoc");
        user.setPassword("1234");

        this.userService.handlCreateUser(user);

        return "new user";
    }
}
