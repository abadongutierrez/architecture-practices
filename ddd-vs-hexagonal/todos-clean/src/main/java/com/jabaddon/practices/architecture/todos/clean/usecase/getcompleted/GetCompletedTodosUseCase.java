package com.jabaddon.practices.architecture.todos.clean.usecase.getcompleted;

import com.jabaddon.practices.architecture.todos.clean.entity.Todo;
import com.jabaddon.practices.architecture.todos.clean.usecase.port.TodoGateway;

import java.util.List;
import java.util.stream.Collectors;

public class GetCompletedTodosUseCase implements GetCompletedTodosInputPort {

    private final TodoGateway todoGateway;

    public GetCompletedTodosUseCase(TodoGateway todoGateway) {
        this.todoGateway = todoGateway;
    }

    @Override
    public GetCompletedTodosResponse execute() {
        List<GetCompletedTodosResponse.TodoItem> items = todoGateway.findByCompleted(true).stream()
                .map(this::toItem)
                .collect(Collectors.toList());

        return new GetCompletedTodosResponse(items);
    }

    private GetCompletedTodosResponse.TodoItem toItem(Todo todo) {
        return new GetCompletedTodosResponse.TodoItem(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
