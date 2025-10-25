package com.jabaddon.practices.architecture.todos.ddd.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Todo {

    private final TodoId id;
    private TodoTitle title;
    private TodoDescription description;
    private TodoStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Todo(TodoId id, TodoTitle title, TodoDescription description, TodoStatus status,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Todo create(TodoTitle title, TodoDescription description) {
        LocalDateTime now = LocalDateTime.now();
        return new Todo(
                TodoId.generate(),
                title,
                description,
                TodoStatus.PENDING,
                now,
                now
        );
    }

    public static Todo reconstitute(TodoId id, TodoTitle title, TodoDescription description,
                                   TodoStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Todo(id, title, description, status, createdAt, updatedAt);
    }

    public void updateTitle(TodoTitle newTitle) {
        this.title = Objects.requireNonNull(newTitle, "Title cannot be null");
        this.updatedAt = LocalDateTime.now();
    }

    public void updateDescription(TodoDescription newDescription) {
        this.description = newDescription;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsCompleted() {
        if (this.status.isCompleted()) {
            throw new IllegalStateException("Todo is already completed");
        }
        this.status = TodoStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsPending() {
        if (this.status.isPending()) {
            throw new IllegalStateException("Todo is already pending");
        }
        this.status = TodoStatus.PENDING;
        this.updatedAt = LocalDateTime.now();
    }

    public TodoId getId() {
        return id;
    }

    public TodoTitle getTitle() {
        return title;
    }

    public TodoDescription getDescription() {
        return description;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isCompleted() {
        return status.isCompleted();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
