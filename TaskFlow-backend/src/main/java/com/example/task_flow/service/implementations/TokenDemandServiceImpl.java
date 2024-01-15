package com.example.task_flow.service.implementations;

import com.example.task_flow.enums.DemandStatus;
import com.example.task_flow.enums.Role;
import com.example.task_flow.enums.TaskStatus;
import com.example.task_flow.enums.TokenType;
import com.example.task_flow.mapper.TaskMapper;
import com.example.task_flow.mapper.TokenDemandMapper;
import com.example.task_flow.model.dto.TokenDemandDTO;
import com.example.task_flow.model.dto.response.TaskResponseDTO;
import com.example.task_flow.model.dto.response.TokenDemandResponseDTO;
import com.example.task_flow.model.entities.Task;
import com.example.task_flow.model.entities.TokenDemand;
import com.example.task_flow.model.entities.User;
import com.example.task_flow.repository.TaskRepository;
import com.example.task_flow.repository.TokenDemandRepository;
import com.example.task_flow.repository.UserRepository;
import com.example.task_flow.service.ITaskService;
import com.example.task_flow.service.ITokenDemandService;
import com.example.task_flow.service.IUserService;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenDemandServiceImpl implements ITokenDemandService {

    private final TokenDemandRepository tokenDemandRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TokenDemandMapper tokenDemandMapper;
    private final TaskMapper taskMapper;
    private final IUserService userService;
    private final ITaskService taskService;

    @Override
    public TokenDemandResponseDTO requestToken(TokenDemandDTO tokenDemandDTO) {
        User requestingUser = getUserById(tokenDemandDTO.getUserId());
        Task requestedTask = getTaskById(tokenDemandDTO.getTaskId());

        if (requestingUser.getRole() != Role.USER) {
            throw new ValidationException("Only users with the role USER can request a token.");
        }

        if (!isUserAssignedToTask(requestingUser, requestedTask)) {
            throw new ValidationException("The requesting user is not assigned to the specified task.");
        }

        if (tokenDemandRepository.existsByTask(requestedTask)) {
            throw new ValidationException("You have already requested a token for the specified task.");
        }

        if (requestedTask.getStatus() != TaskStatus.IN_PROGRESS) {
            throw new ValidationException("Token can only be requested for tasks with status IN_PROGRESS.");
        }

        if (tokenDemandDTO.getType() == TokenType.DELETE) {
            validateDeletionTokens(requestingUser.getId());
        }

        if (tokenDemandDTO.getType() == TokenType.REPLACEMENT) {
            validateModificationTokens(requestingUser.getId());
        }

        TokenDemand tokenDemand = tokenDemandMapper.dtoToEntity(tokenDemandDTO);
        tokenDemand.setUser(requestingUser);
        tokenDemand.setTask(requestedTask);
        tokenDemand.setDemandDate(LocalDateTime.now());
        tokenDemand.setStatus(DemandStatus.PENDING);
        TokenDemand savedTokenDemand = tokenDemandRepository.save(tokenDemand);

        return tokenDemandMapper.entityToDto(savedTokenDemand);
    }

    private boolean isUserAssignedToTask(User user, Task task) {
        return user.equals(task.getAssignedTo());
    }


    private void validateDeletionTokens(Long userId) {
        int monthlyDeletionTokens = tokenDemandRepository.countMonthlyDeletionTokens(
                userId,
                TokenType.DELETE,
                LocalDate.now()
        );

        if (monthlyDeletionTokens >= 1) {
            throw new ValidationException("You have already requested a deletion token this month.");
        }
    }

    private void validateModificationTokens(Long userId) {
        int monthlyModificationTokens = tokenDemandRepository.countMonthlyModificationTokens(
                userId,
                TokenType.REPLACEMENT,
                LocalDate.now()
        );

        if (monthlyModificationTokens >= 2) {
            throw new ValidationException("You have already requested a modification token this month.");
        }
    }

    @Override
    public void processToken(Long tokenId, boolean accept, Long managerId) {
        if (getUserById(managerId).getRole() != Role.MANAGER) {
            throw new ValidationException("Only users with the role MANAGER can process tokens.");
        }

        TokenDemand tokenDemand = tokenDemandRepository.getById(tokenId);
        if (tokenDemand == null) {
            throw new ValidationException("Token not found with ID: " + tokenId);
        }

        if (tokenDemand.getStatus() != DemandStatus.PENDING) {
            throw new ValidationException("Token is already processed.");
        }

        if (accept) {
            tokenDemand.setStatus(DemandStatus.APPROVED);
            if (tokenDemand.getType() == TokenType.DELETE) {
                taskService.deleteTask(tokenDemand.getTask().getId(), tokenDemand.getTask().getCreatedBy().getId());
            }
        } else {
            tokenDemand.setStatus(DemandStatus.REFUSED);
        }

        tokenDemandRepository.save(tokenDemand);
    }

    @Override
    @Transactional
    public TaskResponseDTO replaceTask(Long assignedToUserId, Long tokenDemandId, Long managerId) {
        TokenDemand tokenDemand = tokenDemandRepository.getById(tokenDemandId);
        if (tokenDemand == null) {
            throw new ValidationException("Token not found with ID: " + tokenDemandId);
        }

        if (getUserById(managerId).getRole() != Role.MANAGER) {
            throw new ValidationException("Only users with the role MANAGER can replace tasks.");
        }

        if (tokenDemand.getType() != TokenType.REPLACEMENT) {
            throw new ValidationException("Invalid token type for task replacement.");
        }

        if (tokenDemand.getStatus() != DemandStatus.APPROVED) {
            throw new ValidationException("Token is not approved for task replacement.");
        }

        Task taskToReplace = tokenDemand.getTask();

        if (taskToReplace.getStatus() != TaskStatus.IN_PROGRESS) {
            throw new ValidationException("Only tasks in progress can be replaced.");
        }

        if (taskToReplace.getAssignedTo().getId().equals(assignedToUserId)) {
            throw new ValidationException("Invalid assignment. The task to replace must not be assigned to the same user.");
        }

        taskToReplace.setAssignedTo(getUserById(assignedToUserId));
        taskRepository.save(taskToReplace);

        tokenDemand.setStatus(DemandStatus.CHANGED);
        tokenDemandRepository.save(tokenDemand);

        return taskMapper.entityToDto(taskToReplace);
    }

    @Override
    @Transactional
    public void processPendingTokenRequests() {
        List<TokenDemand> pendingTokenRequests = tokenDemandRepository.findPendingTokenRequests();
        for (TokenDemand tokenDemand : pendingTokenRequests) {
            if (isManagerResponseOverdue(tokenDemand)) {
                User user = userRepository.findById(tokenDemand.getUser().getId()).orElseThrow(() -> new ValidationException("User not found"));
                user.setAdditionalTokens(user.getAdditionalTokens() + 2);
                userRepository.save(user);
            }
        }
    }

    private boolean isManagerResponseOverdue(TokenDemand tokenDemand) {
        LocalDateTime requestDate = tokenDemand.getDemandDate();
        LocalDateTime deadline = requestDate.plusHours(12);
        LocalDateTime now = LocalDateTime.now();

        return now.isAfter(deadline);
    }

    @Override
    public List<TokenDemandResponseDTO> getAllTokenDemands() {
        List<TokenDemand> tokenDemands = tokenDemandRepository.findAll();
        return tokenDemands.stream()
                .map(tokenDemandMapper::entityToDto)
                .toList();
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException("User not found with ID: " + userId));
    }

    private Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ValidationException("Task with ID " + taskId + " not found."));
    }


}
