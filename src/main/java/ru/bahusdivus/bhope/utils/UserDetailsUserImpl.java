package ru.bahusdivus.bhope.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetailsUserImpl extends User {

    private final Long id;

    public UserDetailsUserImpl(String username,
                               String password,
                               Collection<? extends GrantedAuthority> authorities,
                               Long id) {
        super(username, password, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
