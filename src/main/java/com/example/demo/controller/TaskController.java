package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired

    private TaskRepository taskRepository;

    // Getting all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        System.out.println("GET /api/tasks - Getting all tasks");
        List<Task> tasks = taskRepository.findAll();
        System.out.println("GET /api/tasks - Found " + tasks.size() + " tasks");
        return tasks;
    }

    // Getting a single task
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Creating a task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    // Updating a task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id") Long taskId, @RequestBody Task taskDetails) {
        Optional<Task> task = taskRepository.findById(taskId);

        if (task.isPresent()) {
            Task updatedTask = task.get();
            updatedTask.setTitle(taskDetails.getTitle());
            updatedTask.setDescription(taskDetails.getDescription());
            updatedTask.setCompleted(taskDetails.isCompleted());
            taskRepository.save(updatedTask);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deleting a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable(value = "id") Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);

        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Deleting all tasks
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllTasks() {
        taskRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
    
}