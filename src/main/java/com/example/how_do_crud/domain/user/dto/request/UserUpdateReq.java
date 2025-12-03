package com.example.how_do_crud.domain.user.dto.request;

public record UserUpdateReq(
        String email,
        String password
) {
}
