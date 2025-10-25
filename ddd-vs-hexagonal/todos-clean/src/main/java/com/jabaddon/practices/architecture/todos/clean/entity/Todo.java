package com.jabaddon.practices.architecture.todos.clean.entity;

import java.time.LocalDateTime;

/**
 * Entity layer - Enterprise Business Rules
 *
 * Pure business object with no dependencies on frameworks or outer layers.
 * Contains business rules that are true regardless of the application.
 */
public class Todo {

    private final String id;
    private String title;
    private String description;
    private boolean completed;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Todo(String id, String title, String description, boolean completed,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Business rule: Complete a todo
    public void complete() {
        if (completed) {
            throw new IllegalStateException("Todo is already completed");
        }
        this.completed = true;
        this.updatedAt = LocalDateTime.now();
    }

    // Business rule: Uncomplete a todo
    public void uncomplete() {
        if (!completed) {
            throw new IllegalStateException("Todo is not completed");
        }
        this.completed = false;
        this.updatedAt = LocalDateTime.now();
    }

    // Business rule: Update todo content
    public void updateContent(String title, String description) {
        validateTitle(title);
        if (description != null && description.length() > 1000) {
            throw new IllegalArgumentException("Description cannot exceed 1000 characters");
        }
        this.title = title;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    // Business rule: Title validation
    public void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (title.length() > 200) {
            throw new IllegalArgumentException("Title cannot exceed 200 characters");
        }
    }

    // Business rule: Description validation
    public void validateDescription(String description) {
        if (description != null && description.length() > 1000) {
            throw new IllegalArgumentException("Description cannot exceed 1000 characters");
        }
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
