package com.jabaddon.practices.architecture.todos.ddd.domain.model;

public record TodoDescription(String value) {

    public TodoDescription {
        if (value != null && value.length() > 1000) {
            throw new IllegalArgumentException("Description cannot exceed 1000 characters");
        }
    }

    public static TodoDescription empty() {
        return new TodoDescription(null);
    }
}
