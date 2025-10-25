package com.jabaddon.practices.architecture.todos.clean.usecase.complete;

import java.time.LocalDateTime;

public record CompleteTodoResponse(
        String id,
        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
