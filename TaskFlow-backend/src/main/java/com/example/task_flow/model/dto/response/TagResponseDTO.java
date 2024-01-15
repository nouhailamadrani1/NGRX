package com.example.task_flow.model.dto.response;

import com.example.task_flow.model.dto.TaskDTO;
import com.example.task_flow.model.entities.Task;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagResponseDTO {
    private Long id;
    private String name;
}
