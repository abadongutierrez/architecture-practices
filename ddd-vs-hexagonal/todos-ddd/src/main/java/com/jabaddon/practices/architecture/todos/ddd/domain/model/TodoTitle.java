package com.jabaddon.practices.architecture.todos.ddd.domain.model;

import java.util.Objects;

public record TodoTitle(String value) {

    public TodoTitle {
        Objects.requireNonNull(value, "Title cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        if (value.length() > 200) {
            throw new IllegalArgumentException("Title cannot exceed 200 characters");
        }
    }
}
