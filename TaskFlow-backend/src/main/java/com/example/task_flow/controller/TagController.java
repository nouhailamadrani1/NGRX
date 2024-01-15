package com.example.task_flow.controller;

import com.example.task_flow.model.dto.TagDTO;
import com.example.task_flow.model.dto.response.TagResponseDTO;
import com.example.task_flow.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final ITagService tagService;

    @Autowired
    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<TagResponseDTO> createTag(@RequestBody @Valid TagDTO tagDTO) {
        TagResponseDTO createdTag = tagService.createTag(tagDTO);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<TagResponseDTO> updateTag(@PathVariable Long tagId, @RequestBody @Valid TagDTO tagDTO) {
        TagResponseDTO updatedTag = tagService.updateTag(tagId, tagDTO);
        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<TagResponseDTO> getTagById(@PathVariable Long tagId) {
        TagResponseDTO tag = tagService.getTagById(tagId);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TagResponseDTO>> getAllTags() {
        List<TagResponseDTO> tags = tagService.getAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
}

