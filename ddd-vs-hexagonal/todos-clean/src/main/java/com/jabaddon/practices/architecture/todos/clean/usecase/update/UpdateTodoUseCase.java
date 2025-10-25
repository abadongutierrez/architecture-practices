package com.jabaddon.practices.architecture.todos.clean.usecase.update;

import com.jabaddon.practices.architecture.todos.clean.entity.Todo;
import com.jabaddon.practices.architecture.todos.clean.usecase.port.TodoGateway;

public class UpdateTodoUseCase implements UpdateTodoInputPort {

    private final TodoGateway todoGateway;

    public UpdateTodoUseCase(TodoGateway todoGateway) {
        this.todoGateway = todoGateway;
    }

    @Override
    public UpdateTodoResponse execute(UpdateTodoRequest request) {
        Todo todo = todoGateway.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + request.id()));

        todo.updateContent(request.title(), request.description());

        Todo saved = todoGateway.save(todo);

        return new UpdateTodoResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.isCompleted(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
}
