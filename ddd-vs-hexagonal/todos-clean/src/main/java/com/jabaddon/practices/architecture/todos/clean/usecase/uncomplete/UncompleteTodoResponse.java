package com.jabaddon.practices.architecture.todos.clean.usecase.uncomplete;

import java.time.LocalDateTime;

public record UncompleteTodoResponse(
        String id,
        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
