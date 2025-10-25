package com.jabaddon.practices.architecture.todos.hx.application.dto;

import java.time.LocalDateTime;

public record TodoDTO(
        String id,
        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
