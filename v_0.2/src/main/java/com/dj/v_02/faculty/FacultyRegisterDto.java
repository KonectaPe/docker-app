package com.dj.v_02.faculty;

import jakarta.validation.constraints.NotBlank;

public record FacultyRegisterDto(
        @NotBlank
        String name
) {
}
