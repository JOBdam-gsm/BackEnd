package com.example.how_do_crud.domain.user.dto.request;

public class UserIdDTO {
    private Long id;

//    @Email()
    private String email;
    private String password;

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
