package com.epam.esm.dao;

import com.epam.esm.model.SqlRequest;
import com.epam.esm.model.Tag;

import java.util.List;

/**
 * The interface Tag dao. The interface, defines specific operations for working with Tag entity in the DB table.
 */
public interface TagDAO extends BaseDao<Tag> {
    /**
     * Create tags in DB.
     *
     * @param tags the tags to create in the DB
     */
    void add(List<Tag> tags);

    /**
     * Find by name.
     *
     * @param sqlRequest the sql request instance
     * @return the list of Tags from DB
     */
    List<Tag> findByName(SqlRequest sqlRequest);

    /**
     * Find by certificate id.
     *
     * @param id the certificate id to search tags in DB
     * @return the list of Tags from DB
     */
    List<Tag> findByCertificateId(long id);

    /**
     * Find all tags.
     *
     * @return the list of Tags from DB
     */
    List<Tag> findAll();
}
