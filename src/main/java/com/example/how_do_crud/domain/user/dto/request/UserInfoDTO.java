package com.example.how_do_crud.domain.user.dto.request;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;

import java.util.List;

public class UserInfoDTO {
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    public UserInfoDTO(String email, String password){
        this.email = email;
        this.password = password;
    }
    public UserInfoDTO(String email, String password, List<String> roles){
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword(){
        return password;
    }
    public List<String> getRoles() {
        return roles;
    }
}
