package com.dj.v_02.asign;

public record AsignResponseDto(
        String id,
        String courseId,
        String nameCourse,
        String nameTeacher,
        String section,
        String status,
        String semester
) {
    public AsignResponseDto(Asign asign) {
        this(asign.getId(), asign.getCourse().getId(), asign.getCourse().getName(), asign.getUser().getFullName(), asign.getSection().name(), asign.getStatus().name(), String.valueOf(asign.getCourse().getSemester()));
    }
}
