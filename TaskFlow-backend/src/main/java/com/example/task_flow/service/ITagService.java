package com.example.task_flow.service;

import com.example.task_flow.model.dto.TagDTO;
import com.example.task_flow.model.dto.response.TagResponseDTO;

import java.util.List;

public interface ITagService {
    TagResponseDTO createTag(TagDTO tagDTO);
    TagResponseDTO updateTag(Long tagId, TagDTO tagDTO);
    void deleteTag(Long tagId);
    TagResponseDTO getTagById(Long tagId);
    List<TagResponseDTO> getAllTags();
}

