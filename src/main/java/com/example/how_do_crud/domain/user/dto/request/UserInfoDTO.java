package com.example.how_do_crud.domain.user.dto.request;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;

import java.util.ArrayList;
import java.util.List;

public class UserInfoDTO {
    private final String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<String> roles = new ArrayList<>();

    public UserInfoDTO(String email, String password, List<String> roles){
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    public UserInfoDTO(String email, List<String> roles){
        this.email = email;
        this.roles.addAll(roles);
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
