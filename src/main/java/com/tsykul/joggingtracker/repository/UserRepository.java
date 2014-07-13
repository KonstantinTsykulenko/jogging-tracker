package com.tsykul.joggingtracker.repository;

import com.tsykul.joggingtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
public interface UserRepository extends JpaRepository<User, String> {
}