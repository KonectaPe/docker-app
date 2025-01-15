package com.dj.v_02.enrollment;

import jakarta.validation.constraints.NotBlank;

public record EnrollmentRegisterDto(
        @NotBlank
        String courseId,
        @NotBlank
        String userId,
        @NotBlank
        String year,
        @NotBlank
        String cycle,
        @NotBlank
        String unit,
        @NotBlank
        String semester,
        @NotBlank
        String section
) {
}
