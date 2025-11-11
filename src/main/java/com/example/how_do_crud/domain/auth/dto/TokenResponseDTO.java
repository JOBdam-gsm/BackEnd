package com.example.how_do_crud.domain.auth.dto;

import lombok.Builder;

@Builder
public record TokenResponseDTO(
        String accessToken,
        String refreshToken) {
}
