package com.example.task_flow.model.dto;

import com.example.task_flow.enums.Priority;
import com.example.task_flow.enums.TaskStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDate dueDate;

    @NotNull(message = "Created by user ID is required")
    private Long createdByUserId;

    @NotNull(message = "Assigned to user ID is required")
    private Long assignedToUserId;

    private TaskStatus status;

    @NotEmpty(message = "At least one tag is required")
    private List<Long> tagIds;
}
