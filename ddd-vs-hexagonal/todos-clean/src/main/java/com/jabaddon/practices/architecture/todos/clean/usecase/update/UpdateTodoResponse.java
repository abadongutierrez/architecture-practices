package com.jabaddon.practices.architecture.todos.clean.usecase.update;

import java.time.LocalDateTime;

public record UpdateTodoResponse(
        String id,
        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
