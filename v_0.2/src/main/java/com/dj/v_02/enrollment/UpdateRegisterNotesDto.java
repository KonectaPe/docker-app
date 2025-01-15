package com.dj.v_02.enrollment;

public record UpdateRegisterNotesDto(
        String id,
        String codeUser,
        String notes,
        String unit
) {
}
