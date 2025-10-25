package com.jabaddon.practices.architecture.todos.hx.application.service;

/**
 * Exception thrown when a todo is not found.
 */
public class TodoNotFoundException extends RuntimeException {

    public TodoNotFoundException(String message) {
        super(message);
    }

    public TodoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
