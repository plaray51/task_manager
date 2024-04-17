# Simple TaskManager

This is a simple TaskManager application built using Java, Spring Boot, and Oracle Database. It provides basic CRUD (Create, Read, Update, Delete) operations for managing tasks.

## Stack

- Java
- Spring Boot
- Oracle Database
- Spring Data JPA
- JUnit
- Maven
- Spring Web

## Endpoints

### GET All Tasks

```bash
GET http://localhost:8080/api/tasks
```

### Get Task by ID

```bash
GET http://localhost:8080/api/tasks/{id}
```

### POST Create a Task

```bash
POST http://localhost:8080/api/tasks

Request Body:
{
  "title": "Sample Task",
  "description": "This is a sample task description.",
  "completed": false
}
```

### PUT Update a Task

```bash
PUT http://localhost:8080/api/tasks/{id}

Request Body:
{
  "title": "Updated Title",
  "description": "Updated description of the task.",
  "completed": true
}
```

### DELETE Delete A Task

```bash
DELETE http://localhost:8080/api/tasks/{id}
```

### DELETE Delete All Tasks

```bash
DELETE http://localhost:8080/api/tasks/deleteAll
```

## How to Run the Project

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Open a terminal and run the following command to start the application:

```bash
./mvnw spring-boot:run
```

4. To run the tests, execute the following command:

```bash
./mvnw test
```

