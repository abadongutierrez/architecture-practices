package com.jabaddon.practices.architecture.todos.hx.application.port.in;

import com.jabaddon.practices.architecture.todos.hx.application.dto.TodoDTO;

import java.util.Optional;

public interface FindTodoByIdUseCase {

    Optional<TodoDTO> findTodoById(String id);
}
