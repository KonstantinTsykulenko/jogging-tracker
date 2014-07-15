package com.tsykul.joggingtracker.controller;

import com.tsykul.joggingtracker.entity.User;
import com.tsykul.joggingtracker.exception.UserExistsException;
import com.tsykul.joggingtracker.model.*;
import com.tsykul.joggingtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Credentials createUser(@RequestBody @Valid User user) {
        User savedUser = userService.saveUser(user);
        return new Credentials(savedUser.getEmail(), savedUser.getPassword());
    }

    @RequestMapping(value = "/user/delete",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public com.tsykul.joggingtracker.model.ResponseStatus deleteUser(@RequestBody @Valid User user) {
        userService.removeUser(user);
        return new com.tsykul.joggingtracker.model.ResponseStatus(com.tsykul.joggingtracker.model.ResponseStatus.Status.SUCCESS);
    }

    @ExceptionHandler
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionModel handleUserAlreadyExistsException(UserExistsException e) {
        return new ExceptionModel(e.getMessage());
    }

}
