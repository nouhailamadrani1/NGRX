package com.example.task_flow.mapper;

import com.example.task_flow.model.dto.TokenDemandDTO;
import com.example.task_flow.model.dto.response.TokenDemandResponseDTO;
import com.example.task_flow.model.entities.TokenDemand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TaskMapper.class})
public interface TokenDemandMapper {
    TokenDemandMapper INSTANCE = Mappers.getMapper(TokenDemandMapper.class);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "task", source = "task")
    TokenDemandResponseDTO entityToDto(TokenDemand tokenDemand);

    TokenDemand dtoToEntity(TokenDemandDTO tokenDemandDTO);
}
