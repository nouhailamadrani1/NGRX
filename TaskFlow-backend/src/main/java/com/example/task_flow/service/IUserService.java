package com.example.task_flow.service;

import com.example.task_flow.model.dto.UserDTO;
import com.example.task_flow.model.dto.response.UserResponseDTO;
import com.example.task_flow.model.entities.User;

import java.util.List;

public interface IUserService {
    UserResponseDTO createUser(UserDTO userDTO);
    UserResponseDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
    UserResponseDTO getUserById(Long userId);
    List<UserResponseDTO> getAllUsers();
}
