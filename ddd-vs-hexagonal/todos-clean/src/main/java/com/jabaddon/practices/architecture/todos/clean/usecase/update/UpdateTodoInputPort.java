package com.jabaddon.practices.architecture.todos.clean.usecase.update;

public interface UpdateTodoInputPort {

    UpdateTodoResponse execute(UpdateTodoRequest request);
}
