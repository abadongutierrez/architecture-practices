package com.jabaddon.practices.architecture.todos.clean.usecase.getall;

import java.time.LocalDateTime;
import java.util.List;

public record GetAllTodosResponse(List<TodoItem> todos) {

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
