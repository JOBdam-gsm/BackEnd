package com.example.how_do_crud.domain.user.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    public final User user;

    public CustomUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        this.user.getRoles().forEach(R -> {
            authorities.add(() -> R);
        });
        return authorities;
    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }
    @Override
    public String getUsername(){
        return user.getEmail();
    }

    public String getName(){
        return user.getName();
    }
}
