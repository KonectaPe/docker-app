package com.dj.v_02.user;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterDto(
        @NotBlank
        String fullName,
        @NotBlank
        String username,
        @NotBlank
        String password,
        UserRoleEnum role
) {
}
