package com.jabaddon.practices.architecture.todos.transcript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryTodoGateway implements TodoGateway {

    private final Map<String, TodoData> storage = new ConcurrentHashMap<>();

    @Override
    public void insert(TodoData todo) {
        storage.put(todo.getId(), todo);
    }

    @Override
    public void update(TodoData todo) {
        storage.put(todo.getId(), todo);
    }

    @Override
    public void delete(String id) {
        storage.remove(id);
    }

    @Override
    public TodoData findById(String id) {
        return storage.get(id);
    }

    @Override
    public List<TodoData> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<TodoData> findByCompleted(boolean completed) {
        return storage.values().stream()
                .filter(todo -> todo.isCompleted() == completed)
                .collect(Collectors.toList());
    }

    @Override
    public boolean exists(String id) {
        return storage.containsKey(id);
    }

    public void clear() {
        storage.clear();
    }
}
