package com.example.task_flow.mapper;

import com.example.task_flow.model.dto.TagDTO;
import com.example.task_flow.model.dto.response.TagResponseDTO;
import com.example.task_flow.model.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
    TagResponseDTO entityToDto(Tag tag);
    Tag dtoToEntity(TagDTO tagDTO);
}
