package com.jabaddon.practices.architecture.todos.clean.usecase.complete;

public interface CompleteTodoInputPort {

    CompleteTodoResponse execute(CompleteTodoRequest request);
}
