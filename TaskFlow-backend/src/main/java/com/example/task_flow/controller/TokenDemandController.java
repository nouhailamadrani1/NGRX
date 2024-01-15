package com.example.task_flow.controller;

import com.example.task_flow.model.dto.TokenDemandDTO;
import com.example.task_flow.model.dto.response.TokenDemandResponseDTO;
import com.example.task_flow.service.ITokenDemandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/token-demands")
@RequiredArgsConstructor
public class TokenDemandController {

    private final ITokenDemandService tokenDemandService;

    @PostMapping("/request")
    public ResponseEntity<String> requestToken(@RequestBody @Valid TokenDemandDTO tokenDemandDTO) {
        tokenDemandService.requestToken(tokenDemandDTO);
        return ResponseEntity.ok("Request created!!");
    }

    @PostMapping("/process")
    public ResponseEntity<String> processToken(
            @RequestParam Long tokenId,
            @RequestParam boolean accept,
            @RequestParam Long managerId
    ) {
        tokenDemandService.processToken(tokenId, accept, managerId);
        return ResponseEntity.ok("Token processed successfully.");
    }

    @PostMapping("/replace")
    public ResponseEntity<String> replaceTask(
            @RequestParam Long assignedToUserId,
            @RequestParam Long tokenDemandId,
            @RequestParam Long managerId
    ) {
        tokenDemandService.replaceTask(assignedToUserId, tokenDemandId, managerId);
        return ResponseEntity.ok("Task replaced successfully.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<TokenDemandResponseDTO>> getAllTokenDemands() {
        List<TokenDemandResponseDTO> tokenDemands = tokenDemandService.getAllTokenDemands();
        return new ResponseEntity<>(tokenDemands, HttpStatus.OK);
    }
}

