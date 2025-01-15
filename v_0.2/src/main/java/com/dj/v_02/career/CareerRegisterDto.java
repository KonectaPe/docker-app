package com.dj.v_02.career;

import jakarta.validation.constraints.NotBlank;

public record CareerRegisterDto(
        @NotBlank
        String name,
        @NotBlank
        String facultyId
) {
}
