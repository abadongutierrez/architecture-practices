package com.jabaddon.practices.architecture.todos.clean.usecase.findbyid;

import java.time.LocalDateTime;

public record FindTodoByIdResponse(
        String id,
        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
