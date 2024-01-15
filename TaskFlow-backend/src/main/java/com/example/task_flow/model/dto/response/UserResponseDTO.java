package com.example.task_flow.model.dto.response;

import com.example.task_flow.enums.Role;
import com.example.task_flow.model.dto.TaskDTO;
import com.example.task_flow.model.dto.TokenDemandDTO;
import com.example.task_flow.model.entities.Task;
import com.example.task_flow.model.entities.TokenDemand;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String userName;
    private String email;
    private String telephone;
    private Role role;
    private List<TaskDTO> createdTasks;
    private List<TaskDTO> assignedTasks;
    private List<TokenDemandDTO> tokenDemands;
}

