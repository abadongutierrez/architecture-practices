package com.jabaddon.practices.architecture.todos.clean.usecase.port;

import com.jabaddon.practices.architecture.todos.clean.entity.Todo;

import java.util.List;
import java.util.Optional;

/**
 * TodoGateway - Output Port (Use Case Layer)
 *
 * Interface defined in the use case layer, implemented in the adapter layer.
 * Use cases depend on this interface (Dependency Inversion Principle).
 * Adapters implement this interface to provide actual persistence.
 */
public interface TodoGateway {

    Todo save(Todo todo);

    Optional<Todo> findById(String id);

    List<Todo> findAll();

    List<Todo> findByCompleted(boolean completed);

    void delete(String id);

    boolean existsById(String id);
}
