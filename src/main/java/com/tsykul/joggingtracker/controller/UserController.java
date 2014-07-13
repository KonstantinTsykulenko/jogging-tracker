package com.tsykul.joggingtracker.controller;

import com.tsykul.joggingtracker.entity.User;
import com.tsykul.joggingtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public User createUser(@RequestBody @Valid User user) {
        return userService.saveUser(user);
    }

}
