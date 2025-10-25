package com.jabaddon.practices.architecture.todos.clean.usecase.delete;

import com.jabaddon.practices.architecture.todos.clean.usecase.port.TodoGateway;

public class DeleteTodoUseCase implements DeleteTodoInputPort {

    private final TodoGateway todoGateway;

    public DeleteTodoUseCase(TodoGateway todoGateway) {
        this.todoGateway = todoGateway;
    }

    @Override
    public void execute(DeleteTodoRequest request) {
        if (!todoGateway.existsById(request.id())) {
            throw new IllegalArgumentException("Todo not found with id: " + request.id());
        }

        todoGateway.delete(request.id());
    }
}
