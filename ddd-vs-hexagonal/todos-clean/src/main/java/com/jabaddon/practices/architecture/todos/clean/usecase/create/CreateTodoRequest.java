package com.jabaddon.practices.architecture.todos.clean.usecase.create;

/**
 * Request Model for Create Todo Use Case
 */
public record CreateTodoRequest(String title, String description) {
}
