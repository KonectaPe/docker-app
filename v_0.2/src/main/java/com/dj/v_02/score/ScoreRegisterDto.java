package com.dj.v_02.score;

import jakarta.validation.constraints.NotBlank;

public record ScoreRegisterDto(
        @NotBlank
        String score,
        @NotBlank
        String enrollmentId
) {
}
