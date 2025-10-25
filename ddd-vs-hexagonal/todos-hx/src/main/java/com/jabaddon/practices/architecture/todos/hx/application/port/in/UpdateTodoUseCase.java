package com.jabaddon.practices.architecture.todos.hx.application.port.in;

import com.jabaddon.practices.architecture.todos.hx.application.dto.TodoDTO;

public interface UpdateTodoUseCase {

    TodoDTO updateTodo(String id, String title, String description);
}
