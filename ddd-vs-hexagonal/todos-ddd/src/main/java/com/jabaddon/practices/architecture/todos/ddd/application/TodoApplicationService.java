package com.jabaddon.practices.architecture.todos.ddd.application;

import com.jabaddon.practices.architecture.todos.ddd.application.dto.TodoDTO;
import com.jabaddon.practices.architecture.todos.ddd.domain.model.*;
import com.jabaddon.practices.architecture.todos.ddd.domain.repository.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TodoApplicationService {

    private final TodoRepository todoRepository;

    public TodoApplicationService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoDTO createTodo(String title, String description) {
        Todo todo = Todo.create(
                new TodoTitle(title),
                description != null ? new TodoDescription(description) : TodoDescription.empty()
        );
        todoRepository.save(todo);
        return toDTO(todo);
    }

    public TodoDTO updateTodo(String id, String title, String description) {
        Todo todo = todoRepository.findById(TodoId.of(id))
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));

        todo.updateTitle(new TodoTitle(title));
        todo.updateDescription(description != null ? new TodoDescription(description) : TodoDescription.empty());

        todoRepository.save(todo);
        return toDTO(todo);
    }

    public TodoDTO completeTodo(String id) {
        Todo todo = todoRepository.findById(TodoId.of(id))
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));

        todo.markAsCompleted();

        todoRepository.save(todo);
        return toDTO(todo);
    }

    public TodoDTO uncompleteTodo(String id) {
        Todo todo = todoRepository.findById(TodoId.of(id))
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));

        todo.markAsPending();

        todoRepository.save(todo);
        return toDTO(todo);
    }

    public void deleteTodo(String id) {
        TodoId todoId = TodoId.of(id);
        if (!todoRepository.existsById(todoId)) {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
        todoRepository.delete(todoId);
    }

    public Optional<TodoDTO> findTodoById(String id) {
        return todoRepository.findById(TodoId.of(id))
                .map(this::toDTO);
    }

    public List<TodoDTO> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TodoDTO> getCompletedTodos() {
        return todoRepository.findByStatus(TodoStatus.COMPLETED).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TodoDTO> getPendingTodos() {
        return todoRepository.findByStatus(TodoStatus.PENDING).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private TodoDTO toDTO(Todo todo) {
        return new TodoDTO(
                todo.getId().value(),
                todo.getTitle().value(),
                todo.getDescription().value(),
                todo.getStatus().name(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
