package com.tsykul.joggingtracker.exception;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
public class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }
}
