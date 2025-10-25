package com.jabaddon.practices.architecture.todos.clean.usecase;

import com.jabaddon.practices.architecture.todos.clean.gateway.InMemoryTodoGateway;
import com.jabaddon.practices.architecture.todos.clean.usecase.complete.CompleteTodoInputPort;
import com.jabaddon.practices.architecture.todos.clean.usecase.complete.CompleteTodoRequest;
import com.jabaddon.practices.architecture.todos.clean.usecase.complete.CompleteTodoResponse;
import com.jabaddon.practices.architecture.todos.clean.usecase.complete.CompleteTodoUseCase;
import com.jabaddon.practices.architecture.todos.clean.usecase.create.CreateTodoInputPort;
import com.jabaddon.practices.architecture.todos.clean.usecase.create.CreateTodoRequest;
import com.jabaddon.practices.architecture.todos.clean.usecase.create.CreateTodoResponse;
import com.jabaddon.practices.architecture.todos.clean.usecase.create.CreateTodoUseCase;
import com.jabaddon.practices.architecture.todos.clean.usecase.delete.DeleteTodoInputPort;
import com.jabaddon.practices.architecture.todos.clean.usecase.delete.DeleteTodoRequest;
import com.jabaddon.practices.architecture.todos.clean.usecase.delete.DeleteTodoUseCase;
import com.jabaddon.practices.architecture.todos.clean.usecase.findbyid.FindTodoByIdInputPort;
import com.jabaddon.practices.architecture.todos.clean.usecase.findbyid.FindTodoByIdRequest;
import com.jabaddon.practices.architecture.todos.clean.usecase.findbyid.FindTodoByIdResponse;
import com.jabaddon.practices.architecture.todos.clean.usecase.findbyid.FindTodoByIdUseCase;
import com.jabaddon.practices.architecture.todos.clean.usecase.getall.GetAllTodosInputPort;
import com.jabaddon.practices.architecture.todos.clean.usecase.getall.GetAllTodosResponse;
import com.jabaddon.practices.architecture.todos.clean.usecase.getall.GetAllTodosUseCase;
import com.jabaddon.practices.architecture.todos.clean.usecase.getcompleted.GetCompletedTodosInputPort;
import com.jabaddon.practices.architecture.todos.clean.usecase.getcompleted.GetCompletedTodosResponse;
import com.jabaddon.practices.architecture.todos.clean.usecase.getcompleted.GetCompletedTodosUseCase;
import com.jabaddon.practices.architecture.todos.clean.usecase.getpending.GetPendingTodosInputPort;
import com.jabaddon.practices.architecture.todos.clean.usecase.getpending.GetPendingTodosResponse;
import com.jabaddon.practices.architecture.todos.clean.usecase.getpending.GetPendingTodosUseCase;
import com.jabaddon.practices.architecture.todos.clean.usecase.port.TodoGateway;
import com.jabaddon.practices.architecture.todos.clean.usecase.uncomplete.UncompleteTodoInputPort;
import com.jabaddon.practices.architecture.todos.clean.usecase.uncomplete.UncompleteTodoRequest;
import com.jabaddon.practices.architecture.todos.clean.usecase.uncomplete.UncompleteTodoResponse;
import com.jabaddon.practices.architecture.todos.clean.usecase.uncomplete.UncompleteTodoUseCase;
import com.jabaddon.practices.architecture.todos.clean.usecase.update.UpdateTodoInputPort;
import com.jabaddon.practices.architecture.todos.clean.usecase.update.UpdateTodoRequest;
import com.jabaddon.practices.architecture.todos.clean.usecase.update.UpdateTodoResponse;
import com.jabaddon.practices.architecture.todos.clean.usecase.update.UpdateTodoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Clean Architecture Use Case Tests
 *
 * Tests demonstrate:
 * 1. Each use case is tested independently
 * 2. Use cases depend only on gateway interface (mocked/in-memory)
 * 3. No framework dependencies
 * 4. Pure business logic testing
 */
class TodoUseCasesTest {

    private TodoGateway todoGateway;
    private CreateTodoInputPort createTodoUseCase;
    private UpdateTodoInputPort updateTodoUseCase;
    private CompleteTodoInputPort completeTodoUseCase;
    private UncompleteTodoInputPort uncompleteTodoUseCase;
    private DeleteTodoInputPort deleteTodoUseCase;
    private FindTodoByIdInputPort findTodoByIdUseCase;
    private GetAllTodosInputPort getAllTodosUseCase;
    private GetCompletedTodosInputPort getCompletedTodosUseCase;
    private GetPendingTodosInputPort getPendingTodosUseCase;

    @BeforeEach
    void setUp() {
        todoGateway = new InMemoryTodoGateway();

        // Instantiate each use case separately (Clean Architecture principle)
        createTodoUseCase = new CreateTodoUseCase(todoGateway);
        updateTodoUseCase = new UpdateTodoUseCase(todoGateway);
        completeTodoUseCase = new CompleteTodoUseCase(todoGateway);
        uncompleteTodoUseCase = new UncompleteTodoUseCase(todoGateway);
        deleteTodoUseCase = new DeleteTodoUseCase(todoGateway);
        findTodoByIdUseCase = new FindTodoByIdUseCase(todoGateway);
        getAllTodosUseCase = new GetAllTodosUseCase(todoGateway);
        getCompletedTodosUseCase = new GetCompletedTodosUseCase(todoGateway);
        getPendingTodosUseCase = new GetPendingTodosUseCase(todoGateway);
    }

    @Test
    void shouldCreateTodo() {
        CreateTodoRequest request = new CreateTodoRequest("Buy groceries", "Milk, bread, eggs");

        CreateTodoResponse response = createTodoUseCase.execute(request);

        assertThat(response, is(notNullValue()));
        assertThat(response.id(), is(notNullValue()));
        assertThat(response.title(), is(equalTo("Buy groceries")));
        assertThat(response.description(), is(equalTo("Milk, bread, eggs")));
        assertThat(response.completed(), is(false));
        assertThat(response.createdAt(), is(notNullValue()));
        assertThat(response.updatedAt(), is(notNullValue()));
    }

    @Test
    void shouldNotCreateTodoWithEmptyTitle() {
        CreateTodoRequest request = new CreateTodoRequest("", "Description");

        assertThrows(IllegalArgumentException.class, () ->
                createTodoUseCase.execute(request));
    }

    @Test
    void shouldNotCreateTodoWithTitleTooLong() {
        String longTitle = "a".repeat(201);
        CreateTodoRequest request = new CreateTodoRequest(longTitle, "Description");

        assertThrows(IllegalArgumentException.class, () ->
                createTodoUseCase.execute(request));
    }

    @Test
    void shouldFindTodoById() {
        CreateTodoResponse created = createTodoUseCase.execute(
                new CreateTodoRequest("Task", "Description"));

        Optional<FindTodoByIdResponse> found = findTodoByIdUseCase.execute(
                new FindTodoByIdRequest(created.id()));

        assertThat(found.isPresent(), is(true));
        assertThat(found.get().id(), is(equalTo(created.id())));
        assertThat(found.get().title(), is(equalTo("Task")));
    }

    @Test
    void shouldReturnEmptyWhenTodoNotFound() {
        Optional<FindTodoByIdResponse> found = findTodoByIdUseCase.execute(
                new FindTodoByIdRequest("non-existent"));

        assertThat(found.isPresent(), is(false));
    }

    @Test
    void shouldUpdateTodo() {
        CreateTodoResponse created = createTodoUseCase.execute(
                new CreateTodoRequest("Original", "Original description"));

        UpdateTodoResponse updated = updateTodoUseCase.execute(
                new UpdateTodoRequest(created.id(), "Updated", "Updated description"));

        assertThat(updated.title(), is(equalTo("Updated")));
        assertThat(updated.description(), is(equalTo("Updated description")));
    }

    @Test
    void shouldNotUpdateNonExistentTodo() {
        UpdateTodoRequest request = new UpdateTodoRequest("non-existent", "Title", "Desc");

        assertThrows(IllegalArgumentException.class, () ->
                updateTodoUseCase.execute(request));
    }

    @Test
    void shouldCompleteTodo() {
        CreateTodoResponse created = createTodoUseCase.execute(
                new CreateTodoRequest("Task", "Description"));

        CompleteTodoResponse completed = completeTodoUseCase.execute(
                new CompleteTodoRequest(created.id()));

        assertThat(completed.completed(), is(true));
    }

    @Test
    void shouldNotCompleteAlreadyCompletedTodo() {
        CreateTodoResponse created = createTodoUseCase.execute(
                new CreateTodoRequest("Task", "Description"));
        completeTodoUseCase.execute(new CompleteTodoRequest(created.id()));

        assertThrows(IllegalStateException.class, () ->
                completeTodoUseCase.execute(new CompleteTodoRequest(created.id())));
    }

    @Test
    void shouldUncompleteTodo() {
        CreateTodoResponse created = createTodoUseCase.execute(
                new CreateTodoRequest("Task", "Description"));
        completeTodoUseCase.execute(new CompleteTodoRequest(created.id()));

        UncompleteTodoResponse uncompleted = uncompleteTodoUseCase.execute(
                new UncompleteTodoRequest(created.id()));

        assertThat(uncompleted.completed(), is(false));
    }

    @Test
    void shouldNotUncompleteNotCompletedTodo() {
        CreateTodoResponse created = createTodoUseCase.execute(
                new CreateTodoRequest("Task", "Description"));

        assertThrows(IllegalStateException.class, () ->
                uncompleteTodoUseCase.execute(new UncompleteTodoRequest(created.id())));
    }

    @Test
    void shouldDeleteTodo() {
        CreateTodoResponse created = createTodoUseCase.execute(
                new CreateTodoRequest("Task", "Description"));

        deleteTodoUseCase.execute(new DeleteTodoRequest(created.id()));

        Optional<FindTodoByIdResponse> found = findTodoByIdUseCase.execute(
                new FindTodoByIdRequest(created.id()));
        assertThat(found.isPresent(), is(false));
    }

    @Test
    void shouldNotDeleteNonExistentTodo() {
        assertThrows(IllegalArgumentException.class, () ->
                deleteTodoUseCase.execute(new DeleteTodoRequest("non-existent")));
    }

    @Test
    void shouldGetAllTodos() {
        createTodoUseCase.execute(new CreateTodoRequest("Task 1", "Desc 1"));
        createTodoUseCase.execute(new CreateTodoRequest("Task 2", "Desc 2"));
        createTodoUseCase.execute(new CreateTodoRequest("Task 3", "Desc 3"));

        GetAllTodosResponse response = getAllTodosUseCase.execute();

        assertThat(response.todos(), hasSize(3));
    }

    @Test
    void shouldGetCompletedAndPendingTodos() {
        CreateTodoResponse todo1 = createTodoUseCase.execute(
                new CreateTodoRequest("Task 1", "Desc 1"));
        CreateTodoResponse todo2 = createTodoUseCase.execute(
                new CreateTodoRequest("Task 2", "Desc 2"));
        CreateTodoResponse todo3 = createTodoUseCase.execute(
                new CreateTodoRequest("Task 3", "Desc 3"));

        completeTodoUseCase.execute(new CompleteTodoRequest(todo1.id()));
        completeTodoUseCase.execute(new CompleteTodoRequest(todo3.id()));

        GetCompletedTodosResponse completed = getCompletedTodosUseCase.execute();
        GetPendingTodosResponse pending = getPendingTodosUseCase.execute();

        assertThat(completed.todos(), hasSize(2));
        assertThat(pending.todos(), hasSize(1));
        assertThat(pending.todos().get(0).id(), is(equalTo(todo2.id())));
    }
}
