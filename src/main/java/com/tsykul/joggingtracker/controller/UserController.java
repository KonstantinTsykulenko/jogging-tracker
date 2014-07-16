package com.tsykul.joggingtracker.controller;

import com.tsykul.joggingtracker.entity.User;
import com.tsykul.joggingtracker.exception.UserExistsException;
import com.tsykul.joggingtracker.model.Credentials;
import com.tsykul.joggingtracker.model.ExceptionModel;
import com.tsykul.joggingtracker.model.ResponseStatus;
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
    public ResponseStatus deleteUser(@RequestBody @Valid Credentials credentials) {
        userService.removeUser(credentials);
        return new ResponseStatus(ResponseStatus.Status.SUCCESS);
    }

    @ExceptionHandler
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionModel handleUserAlreadyExistsException(UserExistsException e) {
        return new ExceptionModel(e.getMessage());
    }

}
