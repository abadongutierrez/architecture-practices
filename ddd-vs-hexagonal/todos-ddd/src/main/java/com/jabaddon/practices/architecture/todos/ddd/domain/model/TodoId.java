package com.jabaddon.practices.architecture.todos.ddd.domain.model;

import java.util.Objects;
import java.util.UUID;

public record TodoId(String value) {

    public TodoId {
        Objects.requireNonNull(value, "TodoId cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("TodoId cannot be blank");
        }
    }

    public static TodoId generate() {
        return new TodoId(UUID.randomUUID().toString());
    }

    public static TodoId of(String value) {
        return new TodoId(value);
    }
}
