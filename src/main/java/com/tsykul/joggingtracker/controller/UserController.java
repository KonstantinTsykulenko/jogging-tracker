package com.tsykul.joggingtracker.controller;

import com.tsykul.joggingtracker.entity.User;
import com.tsykul.joggingtracker.exception.UserExistsException;
import com.tsykul.joggingtracker.model.ExceptionModel;
import com.tsykul.joggingtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionModel handleUserAlreadyExistsException(UserExistsException e) {
        return new ExceptionModel(e.getMessage());
    }

}
