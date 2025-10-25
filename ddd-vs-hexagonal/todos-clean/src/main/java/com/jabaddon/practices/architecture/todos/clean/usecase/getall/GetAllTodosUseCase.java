package com.jabaddon.practices.architecture.todos.clean.usecase.getall;

import com.jabaddon.practices.architecture.todos.clean.entity.Todo;
import com.jabaddon.practices.architecture.todos.clean.usecase.port.TodoGateway;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllTodosUseCase implements GetAllTodosInputPort {

    private final TodoGateway todoGateway;

    public GetAllTodosUseCase(TodoGateway todoGateway) {
        this.todoGateway = todoGateway;
    }

    @Override
    public GetAllTodosResponse execute() {
        List<GetAllTodosResponse.TodoItem> items = todoGateway.findAll().stream()
                .map(this::toItem)
                .collect(Collectors.toList());

        return new GetAllTodosResponse(items);
    }

    private GetAllTodosResponse.TodoItem toItem(Todo todo) {
        return new GetAllTodosResponse.TodoItem(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
