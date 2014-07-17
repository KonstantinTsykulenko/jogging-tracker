package com.tsykul.joggingtracker.security;

import com.tsykul.joggingtracker.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author KonstantinTsykulenko
 * @since 7/17/2014.
 */
public final class UserUtil {

    private UserUtil() {
    }

    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
