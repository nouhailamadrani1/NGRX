package com.example.task_flow.service;

import com.example.task_flow.model.dto.TaskDTO;
import com.example.task_flow.model.dto.response.TaskResponseDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ITaskService {
    TaskResponseDTO createTask(TaskDTO taskDTO);
    TaskResponseDTO updateTask(Long taskId, TaskDTO taskDTO, Long userId);
    void deleteTask(Long taskId, Long userId);
    TaskResponseDTO getTaskById(Long taskId);
    List<TaskResponseDTO> getAllTasks();
    void updateTaskStatusToDone(Long taskId);
    @Transactional
    void updateOverdueTasksStatus();
}
