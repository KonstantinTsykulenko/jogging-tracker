package com.tsykul.joggingtracker.service;

import com.tsykul.joggingtracker.entity.User;
import com.tsykul.joggingtracker.exception.UserExistsException;
import com.tsykul.joggingtracker.model.Credentials;
import com.tsykul.joggingtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Credentials saveUser(Credentials user) {
        User existing = repository.findOne(user.getEmail());
        if (existing != null) {
            throw new UserExistsException(
                    String.format("There already exists a user with email=%s", user.getEmail()));
        }
        repository.save(new User(user.getEmail(), passwordEncoder.encode(user.getPassword())));
        return user;
    }

    @Override
    @Transactional
    public void removeUser(User user) {
        repository.delete(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findOne(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return user;
    }
}
