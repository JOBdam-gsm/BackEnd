package com.example.how_do_crud.domain.user.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record UserCreateReq(
        String name,
        String email,
        String password,
        List<String> roles) {
}
