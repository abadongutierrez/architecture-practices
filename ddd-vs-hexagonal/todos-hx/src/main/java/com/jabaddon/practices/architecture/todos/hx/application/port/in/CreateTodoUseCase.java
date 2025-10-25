package com.jabaddon.practices.architecture.todos.hx.application.port.in;

import com.jabaddon.practices.architecture.todos.hx.application.dto.TodoDTO;

public interface CreateTodoUseCase {

    TodoDTO createTodo(String title, String description);
}
