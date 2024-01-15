package com.example.task_flow.model.dto.response;

import com.example.task_flow.enums.DemandStatus;
import com.example.task_flow.enums.TokenType;
import com.example.task_flow.model.dto.TaskDTO;
import com.example.task_flow.model.dto.UserDTO;
import com.example.task_flow.model.entities.Task;
import com.example.task_flow.model.entities.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDemandResponseDTO {
    private Long id;
    private LocalDateTime demandDate;
    private DemandStatus status;
    private TokenType type;
    private UserDTO user;
    private TaskDTO task;
}
