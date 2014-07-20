package com.tsykul.joggingtracker.controller;

import com.tsykul.joggingtracker.model.Credentials;
import com.tsykul.joggingtracker.model.SecurityToken;
import com.tsykul.joggingtracker.security.TokenUtils;
import com.tsykul.joggingtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KonstantinTsykulenko
 * @since 7/14/2014.
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public SecurityToken login(@RequestBody Credentials credentials, HttpServletRequest httpServletRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
        String token = TokenUtils.createToken(credentials.getEmail());
        httpServletRequest.getSession().setAttribute("securityToken", token);
        return new SecurityToken(token);
    }

    @RequestMapping(value = "/logout",
            method = RequestMethod.POST)
    public void logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute("securityToken");
    }
}
