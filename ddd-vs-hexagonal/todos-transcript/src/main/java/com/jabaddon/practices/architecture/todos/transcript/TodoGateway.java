package com.jabaddon.practices.architecture.todos.transcript;

import java.util.List;

public interface TodoGateway {

    void insert(TodoData todo);

    void update(TodoData todo);

    void delete(String id);

    TodoData findById(String id);

    List<TodoData> findAll();

    List<TodoData> findByCompleted(boolean completed);

    boolean exists(String id);
}
