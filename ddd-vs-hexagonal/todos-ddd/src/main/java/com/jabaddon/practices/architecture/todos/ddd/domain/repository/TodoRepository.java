package com.jabaddon.practices.architecture.todos.ddd.domain.repository;

import com.jabaddon.practices.architecture.todos.ddd.domain.model.*;
import com.jabaddon.practices.architecture.todos.ddd.domain.repository.dao.TodoDao;
import com.jabaddon.practices.architecture.todos.ddd.domain.repository.dao.TodoPersistenceModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TodoRepository {

    private final TodoDao todoDao;

    public TodoRepository(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    public void save(Todo todo) {
        TodoPersistenceModel model = TodoPersistenceModel.fromAggregate(todo);
        todoDao.save(model);
    }

    public Optional<Todo> findById(TodoId id) {
        return todoDao.findById(id.value())
                .map(TodoPersistenceModel::toAggregate);
    }

    public List<Todo> findAll() {
        return todoDao.findAll().stream()
                .map(TodoPersistenceModel::toAggregate)
                .collect(Collectors.toList());
    }

    public List<Todo> findByStatus(TodoStatus status) {
        return todoDao.findByStatus(status.name()).stream()
                .map(TodoPersistenceModel::toAggregate)
                .collect(Collectors.toList());
    }

    public void delete(TodoId id) {
        todoDao.delete(id.value());
    }

    public boolean existsById(TodoId id) {
        return todoDao.existsById(id.value());
    }
}
