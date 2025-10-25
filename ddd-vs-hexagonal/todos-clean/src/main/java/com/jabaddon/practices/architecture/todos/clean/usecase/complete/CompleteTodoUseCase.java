package com.jabaddon.practices.architecture.todos.clean.usecase.complete;

import com.jabaddon.practices.architecture.todos.clean.entity.Todo;
import com.jabaddon.practices.architecture.todos.clean.usecase.port.TodoGateway;

public class CompleteTodoUseCase implements CompleteTodoInputPort {

    private final TodoGateway todoGateway;

    public CompleteTodoUseCase(TodoGateway todoGateway) {
        this.todoGateway = todoGateway;
    }

    @Override
    public CompleteTodoResponse execute(CompleteTodoRequest request) {
        Todo todo = todoGateway.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + request.id()));

        todo.complete();

        Todo saved = todoGateway.save(todo);

        return new CompleteTodoResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.isCompleted(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
}
