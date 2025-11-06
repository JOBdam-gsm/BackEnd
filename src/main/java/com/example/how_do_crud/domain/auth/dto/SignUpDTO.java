package com.example.how_do_crud.domain.auth.dto;

import lombok.Data;

@Data
public class SignUpDTO {
    private final String email;
    private final String password;
    private final String roles;
}
