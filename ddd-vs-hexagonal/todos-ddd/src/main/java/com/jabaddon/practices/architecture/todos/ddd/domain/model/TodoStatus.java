package com.jabaddon.practices.architecture.todos.ddd.domain.model;

public enum TodoStatus {
    PENDING,
    COMPLETED;

    public boolean isCompleted() {
        return this == COMPLETED;
    }

    public boolean isPending() {
        return this == PENDING;
    }
}
