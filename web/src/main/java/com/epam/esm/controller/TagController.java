package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Tag controller. The class used to manipulate CRD operations on Tag data.
 */
@RestController
@RequestMapping(value = "/v1/tags", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    /**
     * Create a new Tag.
     *
     * @param tagDTO the tagDTO with information to create
     * @return the created tag
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO add(@RequestBody TagDTO tagDTO) {
        return tagService.add(tagDTO);
    }

    /**
     * Remove a Tag by id
     *
     * @param id the id tag to remove
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") long id) {
        tagService.remove(id);
    }

    /**
     * Find a TagDTO by id.
     *
     * @param id the id tag for search
     * @return the TagDTO with the requested id
     */
    @GetMapping("/{id}")
    public TagDTO findById(@PathVariable("id") long id) {
        return tagService.findById(id);
    }

    /**
     * Find all tags.
     *
     * @return the list of TagDTOs
     */
    @GetMapping
    public List<TagDTO> findAll() {
        return tagService.findAll();
    }
}
