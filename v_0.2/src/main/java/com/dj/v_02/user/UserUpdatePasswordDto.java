package com.dj.v_02.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdatePasswordDto(
        @NotBlank
        String id,
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must have at least 8 characters, one uppercase letter, one lowercase letter and one number")
        String password
) {

}
