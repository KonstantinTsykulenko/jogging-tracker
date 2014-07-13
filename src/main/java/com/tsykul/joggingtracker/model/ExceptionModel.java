package com.tsykul.joggingtracker.model;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
public class ExceptionModel {
    private String message;

    public ExceptionModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
