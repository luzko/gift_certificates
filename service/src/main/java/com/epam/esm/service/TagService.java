package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

import java.util.List;
import java.util.Map;

/**
 * The interface Tag service.
 * The interface defines certain operations on tags.
 */
public interface TagService extends BaseService<TagDTO> {
    /**
     * Find the most popular tag.
     *
     * @return the most popular tag
     */
    TagDTO findMostPopular();

    /**
     * Find all tags.
     *
     * @param parameters the parameters
     * @return the list of TagDTO
     */
    List<TagDTO> findAll(Map<String, String> parameters);

    /**
     * Define count tags from DB.
     *
     * @return the count tags from DB
     */
    long defineCount();
}
