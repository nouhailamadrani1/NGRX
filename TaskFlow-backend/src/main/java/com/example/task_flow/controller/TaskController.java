package com.example.task_flow.controller;

import com.example.task_flow.model.dto.TaskDTO;
import com.example.task_flow.model.dto.response.TaskResponseDTO;
import com.example.task_flow.service.ITaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    private final ITaskService taskService;

    @Autowired
    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody @Valid TaskDTO task) {
        taskService.createTask(task);
        return new ResponseEntity<>("created Task", HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}/{userId}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long taskId,
            @RequestBody @Valid TaskDTO task,
            @PathVariable Long userId) {
        TaskResponseDTO updatedTask = taskService.updateTask(taskId, task, userId);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}/{userId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId, @PathVariable Long userId) {
        taskService.deleteTask(taskId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long taskId) {
        TaskResponseDTO task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}/mark-done")
    public ResponseEntity<String> markTaskAsDone(@PathVariable Long taskId) {
        taskService.updateTaskStatusToDone(taskId);
        return ResponseEntity.ok("Task status has been updated to completed.");
    }
}
