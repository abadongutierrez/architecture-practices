package com.jabaddon.practices.architecture.todos.transcript;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TodoTransactionScriptTest {

    private InMemoryTodoGateway gateway;
    private TodoTransactionScript script;

    @BeforeEach
    void setUp() {
        gateway = new InMemoryTodoGateway();
        script = new TodoTransactionScript(gateway);
    }

    @Test
    void shouldCreateTodo() {
        TodoData todo = script.createTodo("Buy groceries", "Milk, bread, eggs");

        assertThat(todo, is(notNullValue()));
        assertThat(todo.getId(), is(notNullValue()));
        assertThat(todo.getTitle(), is(equalTo("Buy groceries")));
        assertThat(todo.getDescription(), is(equalTo("Milk, bread, eggs")));
        assertThat(todo.isCompleted(), is(false));
        assertThat(todo.getCreatedAt(), is(notNullValue()));
        assertThat(todo.getUpdatedAt(), is(notNullValue()));
    }

    @Test
    void shouldNotCreateTodoWithEmptyTitle() {
        assertThrows(IllegalArgumentException.class, () ->
                script.createTodo("", "Description"));
    }

    @Test
    void shouldNotCreateTodoWithTitleTooLong() {
        String longTitle = "a".repeat(201);
        assertThrows(IllegalArgumentException.class, () ->
                script.createTodo(longTitle, "Description"));
    }

    @Test
    void shouldFindTodoById() {
        TodoData createdTodo = script.createTodo("Task", "Description");

        TodoData foundTodo = script.findTodoById(createdTodo.getId());

        assertThat(foundTodo, is(notNullValue()));
        assertThat(foundTodo.getId(), is(equalTo(createdTodo.getId())));
        assertThat(foundTodo.getTitle(), is(equalTo("Task")));
    }

    @Test
    void shouldReturnNullWhenTodoNotFound() {
        TodoData foundTodo = script.findTodoById("non-existent-id");

        assertThat(foundTodo, is(nullValue()));
    }

    @Test
    void shouldUpdateTodo() {
        TodoData createdTodo = script.createTodo("Original Title", "Original Description");

        TodoData updatedTodo = script.updateTodo(
                createdTodo.getId(),
                "Updated Title",
                "Updated Description"
        );

        assertThat(updatedTodo.getTitle(), is(equalTo("Updated Title")));
        assertThat(updatedTodo.getDescription(), is(equalTo("Updated Description")));
        assertThat(updatedTodo.getUpdatedAt(), is(notNullValue()));
    }

    @Test
    void shouldNotUpdateNonExistentTodo() {
        assertThrows(IllegalArgumentException.class, () ->
                script.updateTodo("non-existent", "Title", "Description"));
    }

    @Test
    void shouldCompleteTodo() {
        TodoData createdTodo = script.createTodo("Task", "Description");

        TodoData completedTodo = script.completeTodo(createdTodo.getId());

        assertThat(completedTodo.isCompleted(), is(true));
    }

    @Test
    void shouldNotCompleteAlreadyCompletedTodo() {
        TodoData createdTodo = script.createTodo("Task", "Description");
        script.completeTodo(createdTodo.getId());

        assertThrows(IllegalStateException.class, () ->
                script.completeTodo(createdTodo.getId()));
    }

    @Test
    void shouldUncompleteTodo() {
        TodoData createdTodo = script.createTodo("Task", "Description");
        script.completeTodo(createdTodo.getId());

        TodoData uncompletedTodo = script.uncompleteTodo(createdTodo.getId());

        assertThat(uncompletedTodo.isCompleted(), is(false));
    }

    @Test
    void shouldNotUncompleteNotCompletedTodo() {
        TodoData createdTodo = script.createTodo("Task", "Description");

        assertThrows(IllegalStateException.class, () ->
                script.uncompleteTodo(createdTodo.getId()));
    }

    @Test
    void shouldDeleteTodo() {
        TodoData createdTodo = script.createTodo("Task", "Description");

        script.deleteTodo(createdTodo.getId());

        TodoData foundTodo = script.findTodoById(createdTodo.getId());
        assertThat(foundTodo, is(nullValue()));
    }

    @Test
    void shouldNotDeleteNonExistentTodo() {
        assertThrows(IllegalArgumentException.class, () ->
                script.deleteTodo("non-existent"));
    }

    @Test
    void shouldGetAllTodos() {
        script.createTodo("Task 1", "Description 1");
        script.createTodo("Task 2", "Description 2");
        script.createTodo("Task 3", "Description 3");

        List<TodoData> allTodos = script.getAllTodos();

        assertThat(allTodos, hasSize(3));
    }

    @Test
    void shouldGetCompletedAndPendingTodos() {
        TodoData todo1 = script.createTodo("Task 1", "Description 1");
        TodoData todo2 = script.createTodo("Task 2", "Description 2");
        TodoData todo3 = script.createTodo("Task 3", "Description 3");

        script.completeTodo(todo1.getId());
        script.completeTodo(todo3.getId());

        List<TodoData> completedTodos = script.getCompletedTodos();
        List<TodoData> pendingTodos = script.getPendingTodos();

        assertThat(completedTodos, hasSize(2));
        assertThat(pendingTodos, hasSize(1));
        assertThat(pendingTodos.get(0).getId(), is(equalTo(todo2.getId())));
    }
}
