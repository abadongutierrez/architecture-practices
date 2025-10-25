package com.jabaddon.practices.architecture.todos.hx.application.port.in;

import com.jabaddon.practices.architecture.todos.hx.application.dto.TodoDTO;

import java.util.List;

public interface GetCompletedTodosUseCase {

    List<TodoDTO> getCompletedTodos();
}
