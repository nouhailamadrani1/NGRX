package com.example.task_flow.model.dto;

import com.example.task_flow.enums.DemandStatus;
import com.example.task_flow.enums.TokenType;
import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDemandDTO {
    private LocalDateTime demandDate;

    private DemandStatus status;

    @NotNull(message = "Type cannot be null")
    private TokenType type;

    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be positive")
    private Long userId;

    @NotNull(message = "Task ID cannot be null")
    @Positive(message = "Task ID must be positive")
    private Long taskId;
}
