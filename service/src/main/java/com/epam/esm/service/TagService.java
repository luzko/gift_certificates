package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

import java.util.List;

/**
 * The interface Tag service. The interface defines certain operations on tags.
 */
public interface TagService extends BaseService<TagDTO> {
    /**
     * Find all tags.
     *
     * @return the list of TagDTOs
     */
    List<TagDTO> findAll();
}
