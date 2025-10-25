package com.jabaddon.practices.architecture.todos.clean.usecase.uncomplete;

public interface UncompleteTodoInputPort {

    UncompleteTodoResponse execute(UncompleteTodoRequest request);
}
