package com.jabaddon.practices.architecture.todos.ddd.domain.repository.dao;

import java.util.List;
import java.util.Optional;

public interface TodoDao {

    void save(TodoPersistenceModel model);

    Optional<TodoPersistenceModel> findById(String id);

    List<TodoPersistenceModel> findAll();

    List<TodoPersistenceModel> findByStatus(String status);

    void delete(String id);

    boolean existsById(String id);
}
