package com.tsykul.joggingtracker.entity;

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
    @Column(name = "id", nullable = false, updatable = false)
    @NotNull
    @Size(max = 64)
    private String id;

    @Column(name = "password", nullable = false)
    @NotNull
    @Size(max = 64)
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
