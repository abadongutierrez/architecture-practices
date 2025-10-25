package com.jabaddon.practices.architecture.todos.hx.application.service;

import com.jabaddon.practices.architecture.todos.hx.application.dto.TodoDTO;
import com.jabaddon.practices.architecture.todos.hx.application.port.in.CreateTodoUseCase;
import com.jabaddon.practices.architecture.todos.hx.application.port.in.FindTodoByIdUseCase;
import com.jabaddon.practices.architecture.todos.hx.application.port.in.GetAllTodosUseCase;
import com.jabaddon.practices.architecture.todos.hx.application.port.out.TodoRepository;
import com.jabaddon.practices.architecture.todos.hx.infrastructure.InMemoryTodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;

class TodoServiceTest {

    private TodoRepository todoRepository;
    private CreateTodoUseCase createTodoUseCase;
    private FindTodoByIdUseCase findTodoByIdUseCase;
    private GetAllTodosUseCase getAllTodosUseCase;

    @BeforeEach
    void setUp() {
        todoRepository = new InMemoryTodoRepository();
        TodoService todoService = new TodoService(todoRepository);
        createTodoUseCase = todoService;
        findTodoByIdUseCase = todoService;
        getAllTodosUseCase = todoService;
    }

    @Test
    void shouldCreateTodo() {
        TodoDTO createdTodo = createTodoUseCase.createTodo("Buy groceries", "Milk, bread, eggs");

        assertThat(createdTodo, is(notNullValue()));
        assertThat(createdTodo.id(), is(notNullValue()));
        assertThat(createdTodo.title(), is(equalTo("Buy groceries")));
        assertThat(createdTodo.description(), is(equalTo("Milk, bread, eggs")));
        assertThat(createdTodo.completed(), is(false));
        assertThat(createdTodo.createdAt(), is(notNullValue()));
        assertThat(createdTodo.updatedAt(), is(notNullValue()));
    }

    @Test
    void shouldFindTodoById() {
        TodoDTO createdTodo = createTodoUseCase.createTodo("Buy groceries", "Milk, bread, eggs");

        Optional<TodoDTO> foundTodo = findTodoByIdUseCase.findTodoById(createdTodo.id());

        assertThat(foundTodo.isPresent(), is(true));
        assertThat(foundTodo.get().id(), is(equalTo(createdTodo.id())));
        assertThat(foundTodo.get().title(), is(equalTo("Buy groceries")));
        assertThat(foundTodo.get().description(), is(equalTo("Milk, bread, eggs")));
    }

    @Test
    void shouldReturnEmptyWhenTodoNotFound() {
        Optional<TodoDTO> foundTodo = findTodoByIdUseCase.findTodoById("non-existent-id");

        assertThat(foundTodo.isPresent(), is(false));
    }

    @Test
    void shouldCreateAndFindMultipleTodos() {
        TodoDTO todo1 = createTodoUseCase.createTodo("Task 1", "Description 1");
        TodoDTO todo2 = createTodoUseCase.createTodo("Task 2", "Description 2");
        TodoDTO todo3 = createTodoUseCase.createTodo("Task 3", "Description 3");

        List<TodoDTO> allTodos = getAllTodosUseCase.getAllTodos();

        assertThat(allTodos, hasSize(3));

        Optional<TodoDTO> foundTodo1 = findTodoByIdUseCase.findTodoById(todo1.id());
        Optional<TodoDTO> foundTodo2 = findTodoByIdUseCase.findTodoById(todo2.id());
        Optional<TodoDTO> foundTodo3 = findTodoByIdUseCase.findTodoById(todo3.id());

        assertThat(foundTodo1.isPresent(), is(true));
        assertThat(foundTodo2.isPresent(), is(true));
        assertThat(foundTodo3.isPresent(), is(true));

        assertThat(foundTodo1.get().title(), is(equalTo("Task 1")));
        assertThat(foundTodo2.get().title(), is(equalTo("Task 2")));
        assertThat(foundTodo3.get().title(), is(equalTo("Task 3")));
    }
}
