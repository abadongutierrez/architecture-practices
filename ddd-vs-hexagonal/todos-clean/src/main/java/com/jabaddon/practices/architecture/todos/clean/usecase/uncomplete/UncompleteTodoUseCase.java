package com.jabaddon.practices.architecture.todos.clean.usecase.uncomplete;

import com.jabaddon.practices.architecture.todos.clean.entity.Todo;
import com.jabaddon.practices.architecture.todos.clean.usecase.port.TodoGateway;

public class UncompleteTodoUseCase implements UncompleteTodoInputPort {

    private final TodoGateway todoGateway;

    public UncompleteTodoUseCase(TodoGateway todoGateway) {
        this.todoGateway = todoGateway;
    }

    @Override
    public UncompleteTodoResponse execute(UncompleteTodoRequest request) {
        Todo todo = todoGateway.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + request.id()));

        todo.uncomplete();

        Todo saved = todoGateway.save(todo);

        return new UncompleteTodoResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.isCompleted(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
}
