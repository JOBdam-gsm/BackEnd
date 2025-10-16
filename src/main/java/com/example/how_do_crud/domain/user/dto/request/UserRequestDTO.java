package com.example.how_do_crud.domain.user.dto;

public class UserDTO {
    Long id;
    String email;
    String password;

    public Long getId(){
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword(){
        return password;
    }
}
