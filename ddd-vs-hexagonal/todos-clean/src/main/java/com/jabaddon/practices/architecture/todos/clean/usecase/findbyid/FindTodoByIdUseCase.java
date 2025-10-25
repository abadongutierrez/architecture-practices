package com.jabaddon.practices.architecture.todos.clean.usecase.findbyid;

import com.jabaddon.practices.architecture.todos.clean.entity.Todo;
import com.jabaddon.practices.architecture.todos.clean.usecase.port.TodoGateway;

import java.util.Optional;

public class FindTodoByIdUseCase implements FindTodoByIdInputPort {

    private final TodoGateway todoGateway;

    public FindTodoByIdUseCase(TodoGateway todoGateway) {
        this.todoGateway = todoGateway;
    }

    @Override
    public Optional<FindTodoByIdResponse> execute(FindTodoByIdRequest request) {
        return todoGateway.findById(request.id())
                .map(this::toResponse);
    }

    private FindTodoByIdResponse toResponse(Todo todo) {
        return new FindTodoByIdResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
