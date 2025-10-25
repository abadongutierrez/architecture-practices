package com.jabaddon.practices.architecture.todos.ddd.infrastructure;

import com.jabaddon.practices.architecture.todos.ddd.domain.repository.dao.TodoDao;
import com.jabaddon.practices.architecture.todos.ddd.domain.repository.dao.TodoPersistenceModel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryTodoDao implements TodoDao {

    private final Map<String, TodoPersistenceModel> storage = new ConcurrentHashMap<>();

    @Override
    public void save(TodoPersistenceModel model) {
        storage.put(model.id(), model);
    }

    @Override
    public Optional<TodoPersistenceModel> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<TodoPersistenceModel> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<TodoPersistenceModel> findByStatus(String status) {
        return storage.values().stream()
                .filter(model -> model.status().equals(status))
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
