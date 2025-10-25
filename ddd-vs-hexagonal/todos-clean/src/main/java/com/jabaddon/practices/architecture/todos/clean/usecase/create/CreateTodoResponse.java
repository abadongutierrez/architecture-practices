package com.jabaddon.practices.architecture.todos.clean.usecase.create;

import java.time.LocalDateTime;

/**
 * Response Model for Create Todo Use Case
 */
public record CreateTodoResponse(
        String id,
        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
