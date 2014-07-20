package com.tsykul.joggingtracker.model;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author KonstantinTsykulenko
 * @since 7/14/2014.
 */
public class Credentials {
    @Email
    @NotNull
    @Size(max = 64)
    private String email;
    @NotNull
    @Size(max = 64, min = 6)
    private String password;

    public Credentials() {
    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
