package com.example.task_flow.service;

import com.example.task_flow.model.dto.TokenDemandDTO;
import com.example.task_flow.model.dto.response.TaskResponseDTO;
import com.example.task_flow.model.dto.response.TokenDemandResponseDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ITokenDemandService {
    TokenDemandResponseDTO requestToken(TokenDemandDTO tokenDemandDTO);
    void processToken(Long tokenId, boolean accept, Long managerId);
    TaskResponseDTO replaceTask(Long assignedToUserId, Long tokenDemandId, Long managerId);
    @Transactional
    void processPendingTokenRequests();
    List<TokenDemandResponseDTO> getAllTokenDemands();
}
