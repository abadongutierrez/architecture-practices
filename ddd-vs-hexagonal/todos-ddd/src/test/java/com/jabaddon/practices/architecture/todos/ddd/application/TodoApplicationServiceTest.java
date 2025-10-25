package com.jabaddon.practices.architecture.todos.ddd.application;

import com.jabaddon.practices.architecture.todos.ddd.application.dto.TodoDTO;
import com.jabaddon.practices.architecture.todos.ddd.domain.repository.TodoRepository;
import com.jabaddon.practices.architecture.todos.ddd.infrastructure.InMemoryTodoDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class TodoApplicationServiceTest {

    private TodoRepository todoRepository;
    private TodoApplicationService todoApplicationService;

    @BeforeEach
    void setUp() {
        todoRepository = new TodoRepository(new InMemoryTodoDao());
        todoApplicationService = new TodoApplicationService(todoRepository);
    }

    @Test
    void shouldCreateTodo() {
        TodoDTO createdTodo = todoApplicationService.createTodo("Buy groceries", "Milk, bread, eggs");

        assertThat(createdTodo, is(notNullValue()));
        assertThat(createdTodo.id(), is(notNullValue()));
        assertThat(createdTodo.title(), is(equalTo("Buy groceries")));
        assertThat(createdTodo.description(), is(equalTo("Milk, bread, eggs")));
        assertThat(createdTodo.status(), is(equalTo("PENDING")));
        assertThat(createdTodo.createdAt(), is(notNullValue()));
        assertThat(createdTodo.updatedAt(), is(notNullValue()));
    }

    @Test
    void shouldFindTodoById() {
        TodoDTO createdTodo = todoApplicationService.createTodo("Buy groceries", "Milk, bread, eggs");

        Optional<TodoDTO> foundTodo = todoApplicationService.findTodoById(createdTodo.id());

        assertThat(foundTodo.isPresent(), is(true));
        assertThat(foundTodo.get().id(), is(equalTo(createdTodo.id())));
        assertThat(foundTodo.get().title(), is(equalTo("Buy groceries")));
        assertThat(foundTodo.get().description(), is(equalTo("Milk, bread, eggs")));
    }

    @Test
    void shouldReturnEmptyWhenTodoNotFound() {
        Optional<TodoDTO> foundTodo = todoApplicationService.findTodoById("non-existent-id");

        assertThat(foundTodo.isPresent(), is(false));
    }

    @Test
    void shouldCreateAndFindMultipleTodos() {
        TodoDTO todo1 = todoApplicationService.createTodo("Task 1", "Description 1");
        TodoDTO todo2 = todoApplicationService.createTodo("Task 2", "Description 2");
        TodoDTO todo3 = todoApplicationService.createTodo("Task 3", "Description 3");

        List<TodoDTO> allTodos = todoApplicationService.getAllTodos();

        assertThat(allTodos, hasSize(3));

        Optional<TodoDTO> foundTodo1 = todoApplicationService.findTodoById(todo1.id());
        Optional<TodoDTO> foundTodo2 = todoApplicationService.findTodoById(todo2.id());
        Optional<TodoDTO> foundTodo3 = todoApplicationService.findTodoById(todo3.id());

        assertThat(foundTodo1.isPresent(), is(true));
        assertThat(foundTodo2.isPresent(), is(true));
        assertThat(foundTodo3.isPresent(), is(true));

        assertThat(foundTodo1.get().title(), is(equalTo("Task 1")));
        assertThat(foundTodo2.get().title(), is(equalTo("Task 2")));
        assertThat(foundTodo3.get().title(), is(equalTo("Task 3")));
    }

    @Test
    void shouldCompleteTodo() {
        TodoDTO createdTodo = todoApplicationService.createTodo("Task", "Description");

        TodoDTO completedTodo = todoApplicationService.completeTodo(createdTodo.id());

        assertThat(completedTodo.status(), is(equalTo("COMPLETED")));
    }

    @Test
    void shouldGetCompletedAndPendingTodos() {
        TodoDTO todo1 = todoApplicationService.createTodo("Task 1", "Description 1");
        TodoDTO todo2 = todoApplicationService.createTodo("Task 2", "Description 2");
        TodoDTO todo3 = todoApplicationService.createTodo("Task 3", "Description 3");

        todoApplicationService.completeTodo(todo1.id());
        todoApplicationService.completeTodo(todo3.id());

        List<TodoDTO> completedTodos = todoApplicationService.getCompletedTodos();
        List<TodoDTO> pendingTodos = todoApplicationService.getPendingTodos();

        assertThat(completedTodos, hasSize(2));
        assertThat(pendingTodos, hasSize(1));
        assertThat(pendingTodos.get(0).id(), is(equalTo(todo2.id())));
    }
}
