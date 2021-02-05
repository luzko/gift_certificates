package com.epam.esm.dao;

import com.epam.esm.model.Tag;

import java.util.List;

/**
 * The interface Tag dao.
 * The interface, defines specific operations for working with Tag entity in the DB table.
 */
public interface TagDAO extends BaseDAO<Tag> {

    /**
     * Find by name.
     *
     * @param query the String query to search in DB
     * @return the list of Tags from DB
     */
    List<Tag> findByName(String query);

    /**
     * Find the most popular tag.
     *
     * @return the most popular tag
     */
    Tag findMostPopular();

    /**
     * Find all tags.
     *
     * @param offset the offset count
     * @param limit  the limit count
     * @return the list of Tags from DB
     */
    List<Tag> findAll(int offset, int limit);

    /**
     * Define count tags from DB.
     *
     * @return the count tags from DB
     */
    long defineCount();
}
