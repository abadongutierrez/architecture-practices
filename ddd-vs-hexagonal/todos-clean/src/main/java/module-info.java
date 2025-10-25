module com.jabaddon.practices.architecture.todos.clean {
    // Export use case input ports (for controllers/adapters to use)
    exports com.jabaddon.practices.architecture.todos.clean.usecase.create;
    exports com.jabaddon.practices.architecture.todos.clean.usecase.update;
    exports com.jabaddon.practices.architecture.todos.clean.usecase.complete;
    exports com.jabaddon.practices.architecture.todos.clean.usecase.uncomplete;
    exports com.jabaddon.practices.architecture.todos.clean.usecase.delete;
    exports com.jabaddon.practices.architecture.todos.clean.usecase.findbyid;
    exports com.jabaddon.practices.architecture.todos.clean.usecase.getall;
    exports com.jabaddon.practices.architecture.todos.clean.usecase.getcompleted;
    exports com.jabaddon.practices.architecture.todos.clean.usecase.getpending;

    // Export gateway interface (output port) for implementations
    exports com.jabaddon.practices.architecture.todos.clean.usecase.port;

    // DO NOT export entity layer - only use cases should access entities
    // DO NOT export adapter layer - these are implementations
}
