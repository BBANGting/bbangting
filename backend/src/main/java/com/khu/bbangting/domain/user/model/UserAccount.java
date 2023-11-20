package com.khu.bbangting.domain.user.model;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserAccount extends org.springframework.security.core.userdetails.User {

    @Getter
    private final User user;

    public UserAccount(User user) {
        super(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
        this.user = user;
    }

}
