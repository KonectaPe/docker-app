package com.dj.v_02.course;

import jakarta.validation.constraints.NotBlank;

public record CourseRegisterDto(
        @NotBlank
        String name,
        @NotBlank
        String credits,
        @NotBlank
        String code,
        @NotBlank
        String semester,
        @NotBlank
        String careerId
) {
}
