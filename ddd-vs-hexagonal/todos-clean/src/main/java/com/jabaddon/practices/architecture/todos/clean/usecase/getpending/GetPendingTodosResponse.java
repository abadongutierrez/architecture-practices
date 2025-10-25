package com.jabaddon.practices.architecture.todos.clean.usecase.getpending;

import java.time.LocalDateTime;
import java.util.List;

public record GetPendingTodosResponse(List<TodoItem> todos) {

    public record TodoItem(
            String id,
            String title,
            String description,
            boolean completed,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
    }
}
