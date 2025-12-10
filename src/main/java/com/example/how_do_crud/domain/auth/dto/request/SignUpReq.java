package com.example.how_do_crud.domain.auth.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record SignUpReq(
        String name,
        String email,
        String password,
        List<String> roles) {
}