package com.dj.v_02.enrollment;

import java.time.LocalDateTime;

public record EnrollmentResponseDto(
        String id,
        String courseId,
        String course,
        String user,
        int timesTaken,
        String year,
        String cycle,
        String unit,
        String semester,
        String section,
        String note1,
        String note2,
        String note3,
        String average,
        String status,
        LocalDateTime createdAt
) {
    public EnrollmentResponseDto(Enrollment enrollment) {
        this(enrollment.getId(), enrollment.getCourse().getId(), enrollment.getCourse().getName(), enrollment.getUser().getFullName(), enrollment.getTimesTaken(), String.valueOf(enrollment.getYear()), String.valueOf(enrollment.getCycle()), enrollment.getUnit(), String.valueOf(enrollment.getSemester()), String.valueOf(enrollment.getSection()), enrollment.getNote1(), enrollment.getNote2(), enrollment.getNote3(), enrollment.getAverage(), String.valueOf(enrollment.getStatus()), enrollment.getCreatedAt());
    }
}
