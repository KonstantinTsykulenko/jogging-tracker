package com.tsykul.joggingtracker.entity;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
@Entity
@Table(name = "jt_user")
public class User implements UserDetails {

    @Id
    @Column(nullable = false, updatable = false)
    @Email
    @NotNull
    @Size(max = 64)
    private String email;

    @Column(nullable = false)
    @NotNull
    @Size(max = 64)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<JogRecord> jogRecords;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<JogRecord> getJogRecords() {
        return jogRecords;
    }

    public void setJogRecords(List<JogRecord> jogRecords) {
        this.jogRecords = jogRecords;
    }
}
