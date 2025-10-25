package com.jabaddon.practices.architecture.todos.clean.usecase.update;

public record UpdateTodoRequest(String id, String title, String description) {
}
