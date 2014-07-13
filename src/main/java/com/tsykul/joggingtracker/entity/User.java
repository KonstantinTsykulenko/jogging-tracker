package com.tsykul.joggingtracker.entity;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
@Entity
public class User {

    @Id
    @Column(name = "email", nullable = false, updatable = false)
    @Email
    @NotNull
    @Size(max = 64)
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull
    @Size(max = 64)
    private String password;

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
