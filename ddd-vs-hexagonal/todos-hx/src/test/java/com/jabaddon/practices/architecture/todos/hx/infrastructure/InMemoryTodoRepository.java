package com.jabaddon.practices.architecture.todos.hx.infrastructure;

import com.jabaddon.practices.architecture.todos.hx.application.dto.TodoDTO;
import com.jabaddon.practices.architecture.todos.hx.application.port.out.TodoRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryTodoRepository implements TodoRepository {

    private final Map<String, TodoDTO> storage = new ConcurrentHashMap<>();

    @Override
    public TodoDTO save(TodoDTO todo) {
        storage.put(todo.id(), todo);
        return todo;
    }

    @Override
    public Optional<TodoDTO> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<TodoDTO> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<TodoDTO> findByCompleted(boolean completed) {
        return storage.values().stream()
                .filter(todo -> todo.completed() == completed)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(String id) {
        return storage.containsKey(id);
    }

    public void clear() {
        storage.clear();
    }
}
