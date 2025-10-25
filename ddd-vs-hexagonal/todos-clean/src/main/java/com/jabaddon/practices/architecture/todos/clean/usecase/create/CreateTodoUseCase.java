package com.jabaddon.practices.architecture.todos.clean.usecase.create;

import com.jabaddon.practices.architecture.todos.clean.entity.Todo;
import com.jabaddon.practices.architecture.todos.clean.usecase.port.TodoGateway;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Create Todo Use Case - Interactor
 *
 * Application-specific business rule for creating a todo.
 * Orchestrates the flow: validate → create entity → save → return response.
 */
public class CreateTodoUseCase implements CreateTodoInputPort {

    private final TodoGateway todoGateway;

    public CreateTodoUseCase(TodoGateway todoGateway) {
        this.todoGateway = todoGateway;
    }

    @Override
    public CreateTodoResponse execute(CreateTodoRequest request) {
        // Create entity with generated ID
        LocalDateTime now = LocalDateTime.now();
        Todo todo = new Todo(
                UUID.randomUUID().toString(),
                request.title(),
                request.description(),
                false,
                now,
                now
        );

        // Validate using entity business rules
        todo.validateTitle(request.title());
        todo.validateDescription(request.description());

        // Save via gateway (output port)
        Todo saved = todoGateway.save(todo);

        // Return use-case-specific response model
        return new CreateTodoResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.isCompleted(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
}
