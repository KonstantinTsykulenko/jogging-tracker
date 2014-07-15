package com.tsykul.joggingtracker.model;

/**
 * @author KonstantinTsykulenko
 * @since 7/15/2014.
 */
public class ResponseStatus {
    public enum Status {
        SUCCESS, FAILURE
    }

    private Status status;

    public ResponseStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
