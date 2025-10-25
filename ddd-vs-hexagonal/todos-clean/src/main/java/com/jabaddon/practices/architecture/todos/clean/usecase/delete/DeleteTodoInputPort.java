package com.jabaddon.practices.architecture.todos.clean.usecase.delete;

public interface DeleteTodoInputPort {

    void execute(DeleteTodoRequest request);
}
