package com.tsykul.joggingtracker.model;

/**
 * @author KonstantinTsykulenko
 * @since 7/15/2014.
 */
public class LogoutResponse {
    public enum LogoutStatus {
        SUCCESS, FAILURE
    }

    private LogoutStatus status;

    public LogoutResponse(LogoutStatus status) {
        this.status = status;
    }

    public LogoutStatus getStatus() {
        return status;
    }
}
