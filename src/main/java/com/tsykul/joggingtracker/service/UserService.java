package com.tsykul.joggingtracker.service;

import com.tsykul.joggingtracker.entity.User;
import com.tsykul.joggingtracker.model.Credentials;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author KonstantinTsykulenko
 * @since 7/14/2014.
 */
public interface UserService extends UserDetailsService {
    User saveUser(User user);

    public void removeUser(User user);
}
