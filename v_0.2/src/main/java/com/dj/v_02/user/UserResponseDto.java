package com.dj.v_02.user;

import java.time.LocalDateTime;

public record UserResponseDto(
        String id,
        String fullName,
        String username,
        String role,
        LocalDateTime createdAt
) {
    public UserResponseDto(User user) {
        this(user.getId(), user.getFullName(), user.getUsername(), String.valueOf(user.getRole()), user.getCreatedAt());
    }
}
