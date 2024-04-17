A simple TaskManager
Stack:
Java
Spring Boot
Oracle Database
Spring Data JPA
JUnit
Maven
Spring Web

GET
Get All Tasks
﻿http://localhost:8080/api/tasks﻿

GET
Get Task by ID
﻿http://localhost:8080/api/tasks/{id}﻿

POST
Create a Task
http://localhost:8080/api/tasks

Body
raw (json)
json
{
  "title": "Sample Task",
  "description": "This is a sample task description.",
  "completed": false
}

PUT
Update a task
http://localhost:8080/api/tasks/{id}
Body
raw (json)
json
{
  "title": "Updated Title",
  "description": "Updated description of the task.",
  "completed": true
}

DELETE
Delete A Task
http://localhost:8080/api/tasks/{id}
﻿
DELETE
Delete All Tasks
http://localhost:8080/api/tasks/deleteAll
﻿

