package com.jabaddon.practices.architecture.todos.clean.usecase.create;

/**
 * Input Port (Boundary) for Create Todo Use Case
 *
 * Defines the contract for creating a todo.
 * Controllers will depend on this interface.
 */
public interface CreateTodoInputPort {

    CreateTodoResponse execute(CreateTodoRequest request);
}
