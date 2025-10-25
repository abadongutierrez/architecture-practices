package com.jabaddon.practices.architecture.todos.hx.application.service;

import com.jabaddon.practices.architecture.todos.hx.application.dto.TodoDTO;
import com.jabaddon.practices.architecture.todos.hx.application.port.in.*;
import com.jabaddon.practices.architecture.todos.hx.application.port.out.TodoRepository;
import com.jabaddon.practices.architecture.todos.hx.domain.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TodoService implements
        CreateTodoUseCase,
        UpdateTodoUseCase,
        CompleteTodoUseCase,
        UncompleteTodoUseCase,
        DeleteTodoUseCase,
        FindTodoByIdUseCase,
        GetAllTodosUseCase,
        GetCompletedTodosUseCase,
        GetIncompleteTodosUseCase {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoDTO createTodo(String title, String description) {
        String id = UUID.randomUUID().toString();
        Todo todo = new Todo(id, title, description);
        TodoDTO dto = toDTO(todo);
        return todoRepository.save(dto);
    }

    @Override
    public TodoDTO updateTodo(String id, String title, String description) {
        TodoDTO dto = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));

        Todo todo = toDomain(dto);
        todo.updateTitle(title);
        todo.updateDescription(description);

        return todoRepository.save(toDTO(todo));
    }

    @Override
    public TodoDTO completeTodo(String id) {
        TodoDTO dto = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));

        Todo todo = toDomain(dto);
        todo.markAsCompleted();

        return todoRepository.save(toDTO(todo));
    }

    @Override
    public TodoDTO uncompleteTodo(String id) {
        TodoDTO dto = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));

        Todo todo = toDomain(dto);
        todo.markAsIncomplete();

        return todoRepository.save(toDTO(todo));
    }

    @Override
    public void deleteTodo(String id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }

    @Override
    public Optional<TodoDTO> findTodoById(String id) {
        return todoRepository.findById(id);
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public List<TodoDTO> getCompletedTodos() {
        return todoRepository.findByCompleted(true);
    }

    @Override
    public List<TodoDTO> getIncompleteTodos() {
        return todoRepository.findByCompleted(false);
    }

    private TodoDTO toDTO(Todo todo) {
        return new TodoDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }

    private Todo toDomain(TodoDTO dto) {
        return new Todo(dto.id(), dto.title(), dto.description());
    }
}
