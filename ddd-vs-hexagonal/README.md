# Architecture Practices: Comparative Study

A comparative study of four different architectural patterns applied to the same ToDo application domain, implemented as Java modules (JPMS).

## 📋 Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Architectural Patterns](#architectural-patterns)
  - [Transaction Script (todos-transcript)](#transaction-script-todos-transcript)
  - [Hexagonal Architecture (todos-hx)](#hexagonal-architecture-todos-hx)
  - [Domain-Driven Design (todos-ddd)](#domain-driven-design-todos-ddd)
  - [Clean Architecture (todos-clean)](#clean-architecture-todos-clean)
- [Context Mapping Patterns](#context-mapping-patterns)
- [Comparison Matrix](#comparison-matrix)
- [References](#references)
- [Running the Project](#running-the-project)

---

## Overview

This project demonstrates four fundamental architectural patterns in software development, each implemented as a Java module (JPMS) solving the same problem: a simple ToDo application. The goal is to understand the trade-offs, complexity, and appropriate use cases for each pattern.

## Project Structure

```
ddd-vs-hexagonal/
├── todos-transcript/          # Transaction Script pattern
├── todos-hx/                  # Hexagonal Architecture (Ports & Adapters)
├── todos-ddd/                 # Domain-Driven Design
├── todos-clean/               # Clean Architecture
├── todos-out-persistence/     # (placeholder for infrastructure)
├── todos-in-web/             # (placeholder for web adapters)
└── pom.xml                   # Multi-module Maven parent
```

---

## Architectural Patterns

### Transaction Script (todos-transcript)

**Pattern Origin:** Martin Fowler
**Source:** *Patterns of Enterprise Application Architecture* (2002)

#### Theory

Transaction Script organizes business logic as procedures where each procedure handles a single request from the presentation layer. Each transaction is a script that runs from start to finish, performing all necessary logic for that business transaction.

**Key Characteristics:**
- Procedural organization
- Each method is a complete transaction
- Anemic domain model (data structures with getters/setters)
- Business logic embedded in procedural scripts
- Direct, straightforward approach

#### Implementation Structure

```
todos-transcript/
├── TodoData.java                    # Anemic data structure
├── TodoGateway.java                 # Data access interface
└── TodoTransactionScript.java       # All business transactions
```

**Implementation:**
- [TodoData.java](todos-transcript/src/main/java/com/jabaddon/practices/architecture/todos/transcript/TodoData.java) - Simple data holder with getters/setters
- [TodoGateway.java](todos-transcript/src/main/java/com/jabaddon/practices/architecture/todos/transcript/TodoGateway.java) - Data access interface
- [TodoTransactionScript.java](todos-transcript/src/main/java/com/jabaddon/practices/architecture/todos/transcript/TodoTransactionScript.java) - Single class containing all business transactions (create, update, complete, delete, etc.)

#### When to Use

**Advantages:**
- ✅ Simple and easy to understand
- ✅ Quick to implement
- ✅ Low learning curve
- ✅ Minimal overhead
- ✅ Perfect for CRUD operations

**Disadvantages:**
- ❌ Code duplication across scripts
- ❌ No encapsulation of business rules
- ❌ Difficult to maintain as complexity grows
- ❌ Validation logic scattered

**Best For:**
- Simple CRUD applications
- Reporting systems
- Data transformation tasks
- Prototypes and MVPs
- Applications with minimal business logic

#### References

- Fowler, Martin. *Patterns of Enterprise Application Architecture*. Addison-Wesley, 2002. (Chapter: Transaction Script, p. 110)
- Fowler, Martin. "Transaction Script." martinfowler.com, https://martinfowler.com/eaaCatalog/transactionScript.html

---

### Hexagonal Architecture (todos-hx)

**Pattern Origin:** Alistair Cockburn
**Also Known As:** Ports and Adapters Architecture
**First Published:** 2005

#### Theory

Hexagonal Architecture (Ports and Adapters) aims to create loosely coupled application components that can be easily tested and swapped. The pattern allows an application to be equally driven by users, programs, automated tests, or batch scripts, and to be developed and tested in isolation from its runtime devices and databases.

**Core Concepts:**

1. **Hexagon (Application Core):** Contains business logic, independent of external concerns
2. **Ports:** Interfaces that define how the application communicates with the outside world
   - **Input Ports (Driving/Primary):** Use cases the application offers
   - **Output Ports (Driven/Secondary):** Dependencies the application needs
3. **Adapters:** Implementations that connect ports to external systems
   - **Input Adapters:** REST controllers, CLI, message consumers
   - **Output Adapters:** Database repositories, external API clients

**Key Principles:**
- Dependency Inversion: Core depends on abstractions, not concrete implementations
- Outside depends on inside, never the reverse
- Technology-agnostic core
- Testability through port mocking

#### Implementation Structure

```
todos-hx/
├── domain/
│   └── Todo.java                              # Domain entity with business logic
├── application/
│   ├── port/
│   │   ├── in/                                # Input ports (use cases)
│   │   │   ├── CreateTodoUseCase.java
│   │   │   ├── UpdateTodoUseCase.java
│   │   │   ├── CompleteTodoUseCase.java
│   │   │   └── ... (9 use case interfaces)
│   │   └── out/
│   │       └── TodoRepository.java            # Output port (persistence)
│   ├── service/
│   │   └── TodoService.java                   # Core hexagon (implements all use cases)
│   └── dto/
│       └── TodoDTO.java                       # Data transfer object
└── module-info.java                           # Exports ports, service, and DTO
```

**Implementation:**
- **Input Ports (9 use cases):** [CreateTodoUseCase](todos-hx/src/main/java/com/jabaddon/practices/architecture/todos/hx/application/port/in/CreateTodoUseCase.java), [UpdateTodoUseCase](todos-hx/src/main/java/com/jabaddon/practices/architecture/todos/hx/application/port/in/UpdateTodoUseCase.java), [CompleteTodoUseCase](todos-hx/src/main/java/com/jabaddon/practices/architecture/todos/hx/application/port/in/CompleteTodoUseCase.java), etc.
- **Output Port:** [TodoRepository](todos-hx/src/main/java/com/jabaddon/practices/architecture/todos/hx/application/port/out/TodoRepository.java) - Persistence abstraction
- **Core Hexagon:** [TodoService](todos-hx/src/main/java/com/jabaddon/practices/architecture/todos/hx/application/service/TodoService.java) - Implements all use cases, contains business logic
- **Domain Entity:** [Todo](todos-hx/src/main/java/com/jabaddon/practices/architecture/todos/hx/domain/Todo.java) - Business entity (not exported)
- **DTO:** [TodoDTO](todos-hx/src/main/java/com/jabaddon/practices/architecture/todos/hx/application/dto/TodoDTO.java) - Data transfer object

#### Module Encapsulation (JPMS)

See [module-info.java](todos-hx/src/main/java/module-info.java) - Exports ports, service, and DTO. Domain package is NOT exported (encapsulated).

#### When to Use

**Advantages:**
- ✅ High testability (mock ports)
- ✅ Technology independence
- ✅ Easy to swap implementations
- ✅ Clear separation of concerns
- ✅ Multiple adapters (REST, CLI, gRPC)

**Disadvantages:**
- ❌ More classes and interfaces
- ❌ Higher initial complexity
- ❌ Can be overkill for simple applications
- ❌ Requires discipline to maintain boundaries

**Best For:**
- Applications with multiple interfaces (web, CLI, batch)
- Systems requiring high testability
- Applications that may need to swap databases/frameworks
- Long-lived systems with changing requirements
- Microservices

#### References

- Cockburn, Alistair. "Hexagonal Architecture." alistair.cockburn.us, 2005. https://alistair.cockburn.us/hexagonal-architecture/
- Freeman, Steve, and Nat Pryce. *Growing Object-Oriented Software, Guided by Tests*. Addison-Wesley, 2009. (Discusses Ports and Adapters)
- Dahan, Udi. "The Onion Architecture." jeffreypalermo.com, 2008. (Related layered approach)
- Martin, Robert C. "Clean Architecture." blog.cleancoder.com, 2012. https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html

---

### Domain-Driven Design (todos-ddd)

**Pattern Origin:** Eric Evans
**Source:** *Domain-Driven Design: Tackling Complexity in the Heart of Software* (2003)

#### Theory

Domain-Driven Design is an approach to software development that centers the development on programming a domain model that has a rich understanding of the processes and rules of a domain. DDD is not just about technical patterns—it's about creating a ubiquitous language shared by developers and domain experts, and modeling the domain in code.

**Core Concepts:**

**Strategic Design:**
- **Bounded Context:** Explicit boundary where a domain model is valid
- **Ubiquitous Language:** Shared vocabulary between developers and domain experts
- **Context Mapping:** Relationships between bounded contexts

**Tactical Design Patterns:**

1. **Value Objects:** Immutable objects defined by their attributes, not identity
   - Examples: `TodoId`, `TodoTitle`, `TodoDescription`, `TodoStatus`
   - Self-validating
   - No identity comparison (equality by value)

2. **Entities:** Objects with identity that persists over time
   - Examples: `Todo` (though simple, has identity through `TodoId`)

3. **Aggregates:** Cluster of entities and value objects with defined boundaries
   - `Todo` is an Aggregate Root
   - Controls access to internal entities
   - Enforces invariants

4. **Repositories:** Provide collection-like interface for aggregates
   - Work with domain objects (aggregates)
   - Abstract persistence concerns

5. **Domain Services:** Operations that don't naturally belong to entities/value objects

6. **Application Services:** Orchestrate domain objects, don't contain business logic

#### Implementation Structure

```
todos-ddd/
├── domain/
│   ├── model/                                 # NOT exported (encapsulated)
│   │   ├── Todo.java                         # Aggregate Root
│   │   ├── TodoId.java                       # Value Object
│   │   ├── TodoTitle.java                    # Value Object
│   │   ├── TodoDescription.java              # Value Object
│   │   └── TodoStatus.java                   # Value Object (enum)
│   └── repository/
│       ├── TodoRepository.java               # Domain repository (concrete class)
│       └── dao/                              # Exported
│           ├── TodoDao.java                  # Infrastructure interface
│           └── TodoPersistenceModel.java     # Anti-Corruption Layer
├── application/
│   ├── TodoApplicationService.java           # Orchestration
│   ├── TodoNotFoundException.java            # Application exception
│   └── dto/
│       └── TodoDTO.java                      # Data Transfer Object
└── module-info.java
```

**Implementation:**

**Value Objects (self-validating, immutable):**
- [TodoId](todos-ddd/src/main/java/com/jabaddon/practices/architecture/todos/ddd/domain/model/TodoId.java) - Identity value object
- [TodoTitle](todos-ddd/src/main/java/com/jabaddon/practices/architecture/todos/ddd/domain/model/TodoTitle.java) - Title with validation (max 200 chars)
- [TodoDescription](todos-ddd/src/main/java/com/jabaddon/practices/architecture/todos/ddd/domain/model/TodoDescription.java) - Description with validation (max 1000 chars)
- [TodoStatus](todos-ddd/src/main/java/com/jabaddon/practices/architecture/todos/ddd/domain/model/TodoStatus.java) - Status enumeration

**Aggregate Root:**
- [Todo](todos-ddd/src/main/java/com/jabaddon/practices/architecture/todos/ddd/domain/model/Todo.java) - Aggregate root with factory methods (`create()`, `reconstitute()`), business behavior (`markAsCompleted()`, `markAsPending()`, etc.)

**Repository + DAO Pattern:**
- [TodoRepository](todos-ddd/src/main/java/com/jabaddon/practices/architecture/todos/ddd/domain/repository/TodoRepository.java) - Domain repository (works with Todo aggregates, delegates to DAO)
- [TodoDao](todos-ddd/src/main/java/com/jabaddon/practices/architecture/todos/ddd/domain/repository/dao/TodoDao.java) - Infrastructure interface (works with TodoPersistenceModel)
- [TodoPersistenceModel](todos-ddd/src/main/java/com/jabaddon/practices/architecture/todos/ddd/domain/repository/dao/TodoPersistenceModel.java) - Anti-Corruption Layer between domain and persistence

**Application Layer:**
- [TodoApplicationService](todos-ddd/src/main/java/com/jabaddon/practices/architecture/todos/ddd/application/TodoApplicationService.java) - Orchestrates domain objects
- [TodoDTO](todos-ddd/src/main/java/com/jabaddon/practices/architecture/todos/ddd/application/dto/TodoDTO.java) - Data transfer object

#### Module Encapsulation (JPMS)

See [module-info.java](todos-ddd/src/main/java/module-info.java) - Exports application and DAO interfaces. Domain model package is NOT exported (fully encapsulated).

This ensures:
- Domain model is completely hidden from external modules
- Infrastructure can only implement `TodoDao`
- No external dependency on domain aggregates or value objects

#### When to Use

**Advantages:**
- ✅ Rich domain model with behavior
- ✅ Encapsulated business rules
- ✅ Ubiquitous language in code
- ✅ Strong invariant protection
- ✅ Scalable for complex domains
- ✅ Self-documenting code

**Disadvantages:**
- ❌ High initial learning curve
- ❌ More classes and complexity
- ❌ Overkill for simple CRUD
- ❌ Requires domain expertise
- ❌ Can be over-engineered for simple domains

**Best For:**
- Complex business domains
- Applications with rich business rules
- Long-lived enterprise systems
- Core business differentiators
- Systems where domain knowledge is valuable
- Collaborative environments with domain experts

#### References

**Books:**
- Evans, Eric. *Domain-Driven Design: Tackling Complexity in the Heart of Software*. Addison-Wesley, 2003. (The foundational text)
- Vernon, Vaughn. *Implementing Domain-Driven Design*. Addison-Wesley, 2013. (Practical implementation guide)
- Vernon, Vaughn. *Domain-Driven Design Distilled*. Addison-Wesley, 2016. (Condensed guide)
- Avram, Abel, and Floyd Marinescu. *Domain-Driven Design Quickly*. InfoQ, 2007. (Free online)

**Articles:**
- Evans, Eric. "Domain-Driven Design Reference." domainlanguage.com, 2015. https://www.domainlanguage.com/ddd/reference/
- Fowler, Martin. "Bounded Context." martinfowler.com, 2014. https://martinfowler.com/bliki/BoundedContext.html
- Vernon, Vaughn. "Effective Aggregate Design." dddcommunity.org, 2011.

---

### Clean Architecture (todos-clean)

**Pattern Origin:** Robert C. Martin (Uncle Bob)
**Source:** *Clean Architecture: A Craftsman's Guide to Software Structure and Design* (2017)

#### Theory

Clean Architecture is an approach to software architecture that emphasizes the separation of concerns through strict layering and the Dependency Rule. The architecture organizes code into concentric circles (layers) where dependencies point only inward, toward higher-level policies. This creates systems that are independent of frameworks, testable, and adaptable to change.

**Core Principle - The Dependency Rule:**

> Source code dependencies must point only inward, toward higher-level policies.

**The Four Concentric Layers:**

1. **Entities (Layer 1):** Enterprise Business Rules
   - Core business objects
   - Most general and high-level rules
   - No dependencies on anything

2. **Use Cases (Layer 2):** Application Business Rules
   - Application-specific business rules
   - Orchestrate flow of data to/from entities
   - One use case class per operation
   - Define input/output boundaries (interfaces)

3. **Interface Adapters (Layer 3):** Controllers, Gateways, Presenters
   - Convert data between use case and external formats
   - Implement interfaces defined in use case layer

4. **Frameworks & Drivers (Layer 4):** External interfaces
   - Outermost layer with all the details
   - Web frameworks, databases, UI, external services

**Key Principles:**
- **Screaming Architecture:** Package structure should scream what the application does, not what frameworks it uses
- **Independence:** Business rules testable without UI, database, web server, or any external element
- **One Use Case = One Class:** Each operation gets its own dedicated use case class
- **Use Case Specific Models:** Each use case has its own Request and Response models

#### Implementation Structure

```
todos-clean/
├── entity/
│   └── Todo.java                                  # Enterprise business rules
├── usecase/
│   ├── port/
│   │   └── TodoGateway.java                      # Output port (interface)
│   ├── create/
│   │   ├── CreateTodoInputPort.java              # Input boundary
│   │   ├── CreateTodoRequest.java                # Request model
│   │   ├── CreateTodoResponse.java               # Response model
│   │   └── CreateTodoUseCase.java                # Use case interactor
│   ├── update/, complete/, uncomplete/, delete/   # Other command use cases
│   └── findbyid/, getall/, getcompleted/, getpending/  # Query use cases
├── adapter/
│   └── gateway/ (placeholder)                     # Gateway implementations
└── module-info.java
```

**Implementation:**

**Entity Layer:**
- [Todo.java](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/entity/Todo.java) - Pure business entity with business methods (`complete()`, `uncomplete()`, `updateContent()`, validation)

**Use Case Layer (9 independent use cases):**
- **Create:** [CreateTodoUseCase](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/usecase/create/CreateTodoUseCase.java) with [Request](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/usecase/create/CreateTodoRequest.java)/[Response](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/usecase/create/CreateTodoResponse.java)
- **Update:** [UpdateTodoUseCase](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/usecase/update/UpdateTodoUseCase.java) with Request/Response
- **Complete:** [CompleteTodoUseCase](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/usecase/complete/CompleteTodoUseCase.java) with Request/Response
- **Uncomplete:** [UncompleteTodoUseCase](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/usecase/uncomplete/UncompleteTodoUseCase.java) with Request/Response
- **Delete:** [DeleteTodoUseCase](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/usecase/delete/DeleteTodoUseCase.java) with Request
- **Find:** [FindTodoByIdUseCase](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/usecase/findbyid/FindTodoByIdUseCase.java) with Request/Response
- **GetAll:** [GetAllTodosUseCase](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/usecase/getall/GetAllTodosUseCase.java) with Response
- **GetCompleted:** [GetCompletedTodosUseCase](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/usecase/getcompleted/GetCompletedTodosUseCase.java) with Response
- **GetPending:** [GetPendingTodosUseCase](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/usecase/getpending/GetPendingTodosUseCase.java) with Response

**Gateway Interface (Output Port):**
- [TodoGateway](todos-clean/src/main/java/com/jabaddon/practices/architecture/todos/clean/usecase/port/TodoGateway.java) - Interface defined in use case layer, implemented by adapters

**Adapter Layer:**
- [InMemoryTodoGateway](todos-clean/src/test/java/com/jabaddon/practices/architecture/todos/clean/gateway/InMemoryTodoGateway.java) - Test implementation

#### Module Encapsulation (JPMS)

See [module-info.java](todos-clean/src/main/java/module-info.java) - Exports all use case packages and gateway interface. Entity layer is NOT exported (encapsulated).

#### Dependency Flow Example

```
Controller (Layer 3)
    ↓ depends on
CreateTodoInputPort (Layer 2 - interface)
    ↓ implemented by
CreateTodoUseCase (Layer 2 - interactor)
    ↓ uses
CreateTodoRequest, CreateTodoResponse (Layer 2 - models)
    ↓ depends on
Todo (Layer 1 - entity)
    ↓ depends on
TodoGateway (Layer 2 - interface)
    ↑ implemented by
InMemoryTodoGateway (Layer 3 - adapter)
```

Note: Dependencies always point inward (toward entities). Outer layers know about inner layers, but inner layers never know about outer layers.

#### When to Use

**Advantages:**
- ✅ Maximum testability - test use cases without any framework
- ✅ Framework independence - swap databases, UI, web frameworks easily
- ✅ Screaming architecture - structure reveals application purpose
- ✅ Independent deployment - use cases can be deployed separately
- ✅ Clear boundaries - explicit layer separation
- ✅ Flexible - easy to add new use cases

**Disadvantages:**
- ❌ Many classes - one use case = multiple files (InputPort, Request, Response, UseCase)
- ❌ More complex than simpler patterns for simple CRUD
- ❌ Requires discipline to maintain boundaries
- ❌ Initial learning curve
- ❌ Can feel like over-engineering for small applications

**Best For:**
- Long-lived enterprise applications
- Systems that need to be framework-independent
- Applications requiring maximum testability
- Systems with frequently changing requirements
- Applications that may need multiple delivery mechanisms (web, mobile, CLI, etc.)
- Teams practicing Test-Driven Development (TDD)
- Core business applications where business rules are critical

#### Key Differences from Other Patterns

**Clean vs Hexagonal:**
- Clean: Four explicit layers with one use case per class
- Hexagonal: Two main areas (inside/outside) with use case interfaces

**Clean vs DDD:**
- Clean: Focus on layer boundaries and dependency direction
- DDD: Focus on domain modeling and ubiquitous language

**Clean vs Transaction Script:**
- Clean: Layered with strict dependency rules
- Transaction Script: Flat, procedural organization

#### References

**Books:**
- Martin, Robert C. *Clean Architecture: A Craftsman's Guide to Software Structure and Design*. Prentice Hall, 2017. (The definitive text)
- Martin, Robert C. *Clean Code: A Handbook of Agile Software Craftsmanship*. Prentice Hall, 2008. (Foundational principles)

**Articles:**
- Martin, Robert C. "The Clean Architecture." blog.cleancoder.com, 2012. https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
- Martin, Robert C. "Screaming Architecture." blog.cleancoder.com, 2011. https://blog.cleancoder.com/uncle-bob/2011/09/30/Screaming-Architecture.html
- Martin, Robert C. "The Dependency Inversion Principle." C++ Report, 1996.

**Related:**
- SOLID Principles (especially Dependency Inversion Principle - key to Clean Architecture)

---

## Context Mapping Patterns

The DDD module (`todos-ddd`) demonstrates **Context Mapping** patterns for integration between bounded contexts.

### Current Implementation: Open Host Service + Published Language

The `todos-ddd` module acts as an **upstream** bounded context that defines:

1. **Open Host Service (OHS):** Provides `TodoDao` interface for downstream contexts
2. **Published Language:** Uses `TodoPersistenceModel` as integration contract
3. **Anti-Corruption Layer (ACL):** `TodoRepository` + `TodoPersistenceModel` protect domain model

**Integration Pattern:**

The upstream context (todos-ddd) exports the [dao package](todos-ddd/src/main/java/module-info.java), which includes [TodoDao](todos-ddd/src/main/java/com/jabaddon/practices/architecture/todos/ddd/domain/repository/dao/TodoDao.java) and [TodoPersistenceModel](todos-ddd/src/main/java/com/jabaddon/practices/architecture/todos/ddd/domain/repository/dao/TodoPersistenceModel.java). Downstream infrastructure modules can implement `TodoDao` without ever seeing the domain model (Todo, TodoId, etc.).

### Other DDD Context Mapping Patterns

As explained in Eric Evans' *Domain-Driven Design* (Chapter 14: Maintaining Model Integrity):

1. **Conformist:** Downstream conforms to upstream's model without translation
2. **Customer/Supplier:** Collaborative relationship where supplier serves customer's needs
3. **Partnership:** Mutual dependency, coordinated evolution
4. **Shared Kernel:** Shared subset of domain model (high coupling)
5. **Separate Ways:** No integration, independent evolution
6. **Anti-Corruption Layer:** Downstream protects itself from upstream with translation layer
7. **Open Host Service:** Upstream defines protocol for integration (our implementation)
8. **Published Language:** Well-documented shared language for integration (our implementation)

#### References

- Evans, Eric. *Domain-Driven Design*. Addison-Wesley, 2003. Chapter 14: "Maintaining Model Integrity"
- Vernon, Vaughn. *Implementing Domain-Driven Design*. Addison-Wesley, 2013. Chapter 3: "Context Maps"

---

## Comparison Matrix

| Aspect | Transaction Script | Hexagonal | DDD | Clean Architecture |
|--------|-------------------|-----------|-----|--------------------|
| **Complexity** | Very Low | Medium | High | High |
| **Learning Curve** | Low | Medium | High | Medium-High |
| **Classes/Files** | 3 | 15+ | 12+ | 36 |
| **Domain Model** | Anemic (TodoData) | Entity (Todo) | Rich (Aggregate + VOs) | Entity (Todo) |
| **Business Logic** | In procedural scripts | In domain + service | In domain model | In entities + use cases |
| **Use Case Organization** | Methods in one class | Interfaces + one service | Application service methods | One class per use case |
| **Testability** | Medium | Very High | High | Very High |
| **Modularity** | Low | High | Very High | Very High |
| **Framework Independence** | Low | High | Medium | Very High |
| **Best For** | Simple CRUD | Multiple adapters | Complex domains | Long-lived, testable systems |
| **Overkill For** | Complex business | Simple CRUD | Simple CRUD | Simple CRUD |
| **Maintenance** | Difficult at scale | Good | Excellent | Excellent |
| **Performance** | Fast | Fast | Medium | Fast |
| **Team Size** | Small | Medium-Large | Medium-Large | Medium-Large |
| **Test Count** | 15 | 8 | 6 | 15 |

### Architecture Decision Guide

```
Requirements
│
├─ Simple CRUD, minimal business logic, quick prototype
│  └─ Use: Transaction Script (todos-transcript)
│
├─ Need multiple adapters (web, CLI, batch), high testability
│  └─ Use: Hexagonal Architecture (todos-hx)
│
├─ Complex business domain, rich domain model, ubiquitous language
│  └─ Use: Domain-Driven Design (todos-ddd)
│
└─ Maximum framework independence, long-lived system, TDD focus
   └─ Use: Clean Architecture (todos-clean)
```

---

## Running the Project

### Prerequisites

- Java 21+
- Maven 3.8+

### Build All Modules

```bash
mvn clean install
```

### Run Tests

```bash
# Test all modules
mvn test

# Test specific module
mvn test -pl todos-transcript
mvn test -pl todos-hx
mvn test -pl todos-ddd
mvn test -pl todos-clean
```

### Test Results

Each module has comprehensive tests with Hamcrest matchers:

- **todos-transcript:** 15 tests ✅
- **todos-hx:** 8 tests ✅
- **todos-ddd:** 6 tests ✅
- **todos-clean:** 15 tests ✅

All tests verify:
- CRUD operations
- Business rule enforcement
- Error handling
- Edge cases
- Use case isolation (Clean Architecture)

---

## Key Takeaways

1. **No Silver Bullet:** Each pattern has trade-offs. Choose based on context, team, and requirements.

2. **Start Simple:** Begin with Transaction Script for prototypes. Refactor to more sophisticated patterns when complexity justifies it.

3. **Domain Matters:** If your domain is complex and valuable, invest in DDD. If it's simple CRUD, don't over-engineer.

4. **Testability & Independence:** Both Hexagonal and Clean Architecture excel at testability and framework independence.

5. **Layer Discipline:** Clean Architecture enforces strict dependency rules through explicit layers.

6. **Ports & Adapters:** Hexagonal Architecture shines when you need multiple adapters or technology-agnostic core.

7. **Use Case Clarity:** Clean Architecture makes each use case explicit (one class per operation), while Hexagonal groups them in services.

8. **Encapsulation:** All four patterns use JPMS modules to enforce boundaries at the module level.

9. **Evolution:** Systems can start as Transaction Script and evolve to Hexagonal, Clean, or DDD as needs grow.

10. **Team Alignment:** Choose DDD when domain experts are available; Clean/Hexagonal for engineering-driven teams focused on testability.

---

## Further Reading

### Books

- **Fowler, Martin.** *Patterns of Enterprise Application Architecture*. Addison-Wesley, 2002.
- **Evans, Eric.** *Domain-Driven Design: Tackling Complexity in the Heart of Software*. Addison-Wesley, 2003.
- **Vernon, Vaughn.** *Implementing Domain-Driven Design*. Addison-Wesley, 2013.
- **Vernon, Vaughn.** *Domain-Driven Design Distilled*. Addison-Wesley, 2016.
- **Martin, Robert C.** *Clean Architecture: A Craftsman's Guide to Software Structure and Design*. Prentice Hall, 2017.
- **Newman, Sam.** *Building Microservices*. O'Reilly, 2015. (Chapter on DDD and Bounded Contexts)

### Online Resources

- **Hexagonal Architecture:** https://alistair.cockburn.us/hexagonal-architecture/
- **Clean Architecture:** https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
- **Martin Fowler's Catalog:** https://martinfowler.com/eaaCatalog/
- **DDD Community:** https://www.dddcommunity.org/
- **Domain Language (Eric Evans):** https://www.domainlanguage.com/

---

## License

This project is for educational purposes, demonstrating architectural patterns.

## Author

Created as a comparative study of architectural patterns in Java with JPMS modules.
