package com.tsykul.joggingtracker.service;

import com.tsykul.joggingtracker.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author KonstantinTsykulenko
 * @since 7/14/2014.
 */
public interface UserService extends UserDetailsService {
    User saveUser(User user);
}
