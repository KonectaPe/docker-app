package com.dj.v_02.course;

import java.time.LocalDateTime;

public record CourseResponseDto(
        String id,
        String name,
        String credits,
        String code,
        String semester,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String careerId,
        String facultyId
) {
    public CourseResponseDto(Course course) {
        this(
                course.getId(),
                course.getName(),
                course.getCredits(),
                course.getCode(),
                String.valueOf(course.getSemester()),
                course.getCreatedAt(),
                course.getUpdatedAt(),
                course.getCareer().getId(),
                course.getCareer().getFaculty().getId()
        );
    }
}
