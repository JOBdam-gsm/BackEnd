package com.example.how_do_crud.domain.auth.dto.request;

public record LoginReq(
        String email,
        String password) {
    public String email(){
        return this.email;
    }
    public String password(){
        return this.password;
    }
}
