package com.dj.v_02.asign;

import jakarta.validation.constraints.NotBlank;

public record AsignRegisterDto(
        @NotBlank
        String courseId,
        @NotBlank
        String userId,
        @NotBlank
        String section
) {
}
