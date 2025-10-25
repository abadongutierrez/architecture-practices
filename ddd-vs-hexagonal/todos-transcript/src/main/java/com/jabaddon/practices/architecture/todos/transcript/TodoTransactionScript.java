package com.jabaddon.practices.architecture.todos.transcript;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TodoTransactionScript {

    private final TodoGateway todoGateway;

    public TodoTransactionScript(TodoGateway todoGateway) {
        this.todoGateway = todoGateway;
    }

    public TodoData createTodo(String title, String description) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (title.length() > 200) {
            throw new IllegalArgumentException("Title cannot exceed 200 characters");
        }

        if (description != null && description.length() > 1000) {
            throw new IllegalArgumentException("Description cannot exceed 1000 characters");
        }

        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        TodoData todo = new TodoData();
        todo.setId(id);
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCompleted(false);
        todo.setCreatedAt(now);
        todo.setUpdatedAt(now);

        todoGateway.insert(todo);

        return todo;
    }

    public TodoData updateTodo(String id, String title, String description) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }

        TodoData todo = todoGateway.findById(id);
        if (todo == null) {
            throw new IllegalArgumentException("Todo not found with id: " + id);
        }

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (title.length() > 200) {
            throw new IllegalArgumentException("Title cannot exceed 200 characters");
        }

        if (description != null && description.length() > 1000) {
            throw new IllegalArgumentException("Description cannot exceed 1000 characters");
        }

        todo.setTitle(title);
        todo.setDescription(description);
        todo.setUpdatedAt(LocalDateTime.now());

        todoGateway.update(todo);

        return todo;
    }

    public TodoData completeTodo(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }

        TodoData todo = todoGateway.findById(id);
        if (todo == null) {
            throw new IllegalArgumentException("Todo not found with id: " + id);
        }

        if (todo.isCompleted()) {
            throw new IllegalStateException("Todo is already completed");
        }

        todo.setCompleted(true);
        todo.setUpdatedAt(LocalDateTime.now());

        todoGateway.update(todo);

        return todo;
    }

    public TodoData uncompleteTodo(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }

        TodoData todo = todoGateway.findById(id);
        if (todo == null) {
            throw new IllegalArgumentException("Todo not found with id: " + id);
        }

        if (!todo.isCompleted()) {
            throw new IllegalStateException("Todo is not completed");
        }

        todo.setCompleted(false);
        todo.setUpdatedAt(LocalDateTime.now());

        todoGateway.update(todo);

        return todo;
    }

    public void deleteTodo(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }

        if (!todoGateway.exists(id)) {
            throw new IllegalArgumentException("Todo not found with id: " + id);
        }

        todoGateway.delete(id);
    }

    public TodoData findTodoById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }

        return todoGateway.findById(id);
    }

    public List<TodoData> getAllTodos() {
        return todoGateway.findAll();
    }

    public List<TodoData> getCompletedTodos() {
        return todoGateway.findByCompleted(true);
    }

    public List<TodoData> getPendingTodos() {
        return todoGateway.findByCompleted(false);
    }
}
