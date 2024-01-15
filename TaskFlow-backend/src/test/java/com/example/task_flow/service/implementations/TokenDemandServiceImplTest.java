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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TokenDemandServiceImplTest {

    @InjectMocks
    private TokenDemandServiceImpl tokenDemandService;

    @Mock
    private TokenDemandRepository tokenDemandRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void processToken() {
        Long tokenId = 1L;
        boolean accept = true;
        Long managerId = 2L;

        TokenDemand tokenDemand = createValidTokenDemand();
        tokenDemand.setStatus(DemandStatus.PENDING);

        when(userRepository.findById(managerId)).thenReturn(Optional.of(createUser(Role.MANAGER)));
        when(tokenDemandRepository.getById(tokenId)).thenReturn(tokenDemand);
        when(tokenDemandRepository.save(any(TokenDemand.class))).thenReturn(tokenDemand);

        tokenDemandService.processToken(tokenId, accept, managerId);

        assertThat(tokenDemand.getStatus()).isEqualTo(accept ? DemandStatus.APPROVED : DemandStatus.REFUSED);
    }

    @Test
    void processPendingToken() {
        TokenDemand overdueTokenDemand = createValidTokenDemand();
        overdueTokenDemand.setStatus(DemandStatus.PENDING);
        overdueTokenDemand.setDemandDate(LocalDateTime.now().minusHours(12));

        User user = createUser(Role.USER);
        user.setAdditionalTokens(2);

        List<TokenDemand> pendingTokenRequests = new ArrayList<>();
        pendingTokenRequests.add(overdueTokenDemand);

        when(tokenDemandRepository.findPendingTokenRequests()).thenReturn(pendingTokenRequests);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        tokenDemandService.processPendingTokenRequests();

        assertThat(user.getAdditionalTokens()).isEqualTo(4);
    }

    private TokenDemandDTO createValidTokenDemandDTO() {
        return TokenDemandDTO.builder()
                .userId(1L)
                .taskId(2L)
                .type(TokenType.REPLACEMENT)
                .build();
    }

    private TokenDemand createValidTokenDemand() {
        TokenDemand tokenDemand = new TokenDemand();
        tokenDemand.setId(1L);
        tokenDemand.setUser(createUser(Role.USER));
        tokenDemand.setTask(createTask(TaskStatus.IN_PROGRESS));
        tokenDemand.setType(TokenType.REPLACEMENT);
        tokenDemand.setDemandDate(LocalDateTime.now());
        tokenDemand.setStatus(DemandStatus.PENDING);
        return tokenDemand;
    }

    private User createUser(Role role) {
        User user = new User();
        user.setId(1L);
        user.setRole(role);
        return user;
    }

    private Task createTask(TaskStatus status) {
        Task task = new Task();
        task.setId(2L);
        task.setStatus(status);
        return task;
    }
}
