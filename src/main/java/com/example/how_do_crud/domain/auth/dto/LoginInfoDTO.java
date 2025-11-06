package com.example.how_do_crud.domain.auth.dto;

import com.example.how_do_crud.domain.user.entity.CustomUserDetails;

public record LoginInfoDTO(
        String email,
        String password) {
    public LoginInfoDTO(CustomUserDetails user){
        this(user.getUsername(), user.getPassword());
    }
    public String email(){
        return this.email;
    }
    public String password(){
        return this.password;
    }
}
