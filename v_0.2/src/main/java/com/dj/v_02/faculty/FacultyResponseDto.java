package com.dj.v_02.faculty;

import java.time.LocalDateTime;

public record FacultyResponseDto(
        String id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public FacultyResponseDto(Faculty faculty) {
        this(faculty.getId(), faculty.getName(), faculty.getCreatedAt(), faculty.getUpdatedAt());
    }
}
