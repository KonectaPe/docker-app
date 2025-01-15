package com.dj.v_02.score;

import java.time.LocalDateTime;

public record ScoreResponseDto(
        String id,
        String score,
        String enrollmentId,
        LocalDateTime createdAt
) {
    public ScoreResponseDto(Score score) {
        this(score.getId(), score.getScore(), score.getEnrollment().getId(), score.getCreatedAt());
    }
}
