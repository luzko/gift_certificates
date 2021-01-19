package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import com.epam.esm.util.builder.impl.TagHateoasBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * The type Tag controller.
 * The class used to manipulate CRUD operations on Tag data.
 */
@RestController
@RequestMapping(value = "/v1/tags", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagHateoasBuilder tagHateoasBuilder;

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
    public ResponseEntity<Void> remove(@PathVariable("id") long id) {
        tagService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Find a TagDTO by id.
     *
     * @param id the id tag for search
     * @return the TagDTO with the requested id
     */
    @GetMapping("/{id}")
    public TagDTO findById(@PathVariable("id") long id) {
        return tagHateoasBuilder.addLinks(tagService.findById(id));
    }

    /**
     * Find the most popular tag.
     *
     * @return the most popular tag
     */
    @GetMapping("/popular")
    public TagDTO findMostPopular() {
        return tagService.findMostPopular();
    }

    /**
     * Find all tags.
     *
     * @param params the search parameters
     * @return the list of TagDTO
     */
    @GetMapping
    public RepresentationModel<TagDTO> findAll(@RequestParam Map<String, String> params) {
        Map<String, String> parameters = new LinkedCaseInsensitiveMap<>();
        parameters.putAll(params);
        List<TagDTO> tags = tagService.findAll(parameters);
        long count = tagService.defineCount();
        return tagHateoasBuilder.addLinks(tags, parameters, count);
    }
}
