// UserController.java
package com.example.task_flow.controller;

import com.example.task_flow.model.dto.UserDTO;
import com.example.task_flow.model.dto.response.UserResponseDTO;
import com.example.task_flow.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserDTO user) {
        UserResponseDTO createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        UserResponseDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserDTO userDTO
    ) {
        UserResponseDTO updatedUser = userService.updateUser(userId, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
