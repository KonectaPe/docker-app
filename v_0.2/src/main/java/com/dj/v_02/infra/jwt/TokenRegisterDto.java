package com.dj.v_02.infra.jwt;

import jakarta.validation.constraints.NotBlank;

public record TokenRegisterDto(
        @NotBlank
        String token
) {
}
