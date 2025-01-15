package com.dj.v_02.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserLoginDto(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotNull
        UserRoleEnum role
) {
}
