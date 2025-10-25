package com.jabaddon.practices.architecture.todos.ddd.application;

public class TodoNotFoundException extends RuntimeException {

    public TodoNotFoundException(String message) {
        super(message);
    }
}
