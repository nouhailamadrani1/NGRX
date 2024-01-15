package com.example.task_flow.mapper;

import com.example.task_flow.model.dto.UserDTO;
import com.example.task_flow.model.dto.response.UserResponseDTO;
import com.example.task_flow.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TaskMapper.class, TokenDemandMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(target = "createdTasks", source = "createdTasks"),
            @Mapping(target = "assignedTasks", source = "assignedTasks"),
            @Mapping(target = "tokenDemands", source = "tokenDemands")
    })
    UserResponseDTO entityToDto(User user);

    User dtoToEntity(UserDTO userDTO);

    List<UserResponseDTO> entitiesToDtos(List<User> users);
    List<User> dtosToEntities(List<UserDTO> userDTOs);
}
