package com.jabaddon.practices.architecture.todos.clean.usecase.findbyid;

import java.util.Optional;

public interface FindTodoByIdInputPort {

    Optional<FindTodoByIdResponse> execute(FindTodoByIdRequest request);
}
