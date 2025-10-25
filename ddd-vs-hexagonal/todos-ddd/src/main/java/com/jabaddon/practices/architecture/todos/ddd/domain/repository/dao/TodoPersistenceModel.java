package com.jabaddon.practices.architecture.todos.ddd.domain.repository.dao;

import com.jabaddon.practices.architecture.todos.ddd.domain.model.*;

import java.time.LocalDateTime;

public record TodoPersistenceModel(
        String id,
        String title,
        String description,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static TodoPersistenceModel fromAggregate(Todo todo) {
        return new TodoPersistenceModel(
                todo.getId().value(),
                todo.getTitle().value(),
                todo.getDescription().value(),
                todo.getStatus().name(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }

    public Todo toAggregate() {
        return Todo.reconstitute(
                TodoId.of(id),
                new TodoTitle(title),
                description != null ? new TodoDescription(description) : TodoDescription.empty(),
                TodoStatus.valueOf(status),
                createdAt,
                updatedAt
        );
    }
}
