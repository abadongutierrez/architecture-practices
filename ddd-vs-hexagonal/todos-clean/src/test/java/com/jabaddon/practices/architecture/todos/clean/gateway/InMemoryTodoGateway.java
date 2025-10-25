package com.jabaddon.practices.architecture.todos.clean.gateway;

import com.jabaddon.practices.architecture.todos.clean.entity.Todo;
import com.jabaddon.practices.architecture.todos.clean.usecase.port.TodoGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-Memory implementation of TodoGateway for testing.
 *
 * This is an adapter (Layer 3) that implements the gateway interface
 * defined in the use case layer (Layer 2).
 */
public class InMemoryTodoGateway implements TodoGateway {

    private final Map<String, Todo> storage = new ConcurrentHashMap<>();

    @Override
    public Todo save(Todo todo) {
        storage.put(todo.getId(), todo);
        return todo;
    }

    @Override
    public Optional<Todo> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Todo> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Todo> findByCompleted(boolean completed) {
        return storage.values().stream()
                .filter(todo -> todo.isCompleted() == completed)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
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
