package com.jabaddon.practices.architecture.todos.ddd.application.dto;

import java.time.LocalDateTime;

public record TodoDTO(
        String id,
        String title,
        String description,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
