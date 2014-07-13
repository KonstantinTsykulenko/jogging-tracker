package com.tsykul.joggingtracker.service;

import com.tsykul.joggingtracker.entity.User;
import com.tsykul.joggingtracker.exception.UserExistsException;
import com.tsykul.joggingtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User saveUser(User user) {
        User existing = repository.findOne(user.getEmail());
        if (existing != null) {
            throw new UserExistsException(
                    String.format("There already exists a user with email=%s", user.getEmail()));
        }
        return repository.save(user);
    }

}
