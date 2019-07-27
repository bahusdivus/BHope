package ru.bahusdivus.bhope.utils;

import org.springframework.security.core.userdetails.User;

import java.util.HashSet;

public class UserDetailsUserImpl extends User {

    private final Long id;
    private final Boolean admin;

    public UserDetailsUserImpl(String username,
                               String password,
                               Long id, Boolean admin) {
        super(username, password, new HashSet<>());
        this.id = id;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public Boolean getAdmin() {
        return admin;
    }
}
