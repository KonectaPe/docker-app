package com.dj.v_02.career;

import java.time.LocalDateTime;

public record CareerResponseDto(
        String id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String facultyId
) {
    public CareerResponseDto(Career career) {
        this(career.getId(), career.getName(), career.getCreatedAt(), career.getUpdatedAt(), career.getFaculty().getId());
    }
}
