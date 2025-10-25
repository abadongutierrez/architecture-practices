package com.jabaddon.practices.architecture.todos.clean.usecase.getpending;

import com.jabaddon.practices.architecture.todos.clean.entity.Todo;
import com.jabaddon.practices.architecture.todos.clean.usecase.port.TodoGateway;

import java.util.List;
import java.util.stream.Collectors;

public class GetPendingTodosUseCase implements GetPendingTodosInputPort {

    private final TodoGateway todoGateway;

    public GetPendingTodosUseCase(TodoGateway todoGateway) {
        this.todoGateway = todoGateway;
    }

    @Override
    public GetPendingTodosResponse execute() {
        List<GetPendingTodosResponse.TodoItem> items = todoGateway.findByCompleted(false).stream()
                .map(this::toItem)
                .collect(Collectors.toList());

        return new GetPendingTodosResponse(items);
    }

    private GetPendingTodosResponse.TodoItem toItem(Todo todo) {
        return new GetPendingTodosResponse.TodoItem(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
