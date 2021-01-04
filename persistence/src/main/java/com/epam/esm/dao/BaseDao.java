package com.epam.esm.dao;

import java.util.Optional;

/**
 * The interface Base dao. General interface, defines basic operations for working with the DB tables.
 *
 * @param <T> the entity type
 */
public interface BaseDao<T> {
    /**
     * Create entity in DB.
     *
     * @param t the entity to create in the DB
     * @return the created entity
     */
    T add(T t);

    /**
     * Remove entity in database.
     *
     * @param id the entity id to remove from DB
     * @return the boolean value that indicates whether the object has been removed
     */
    boolean remove(long id);

    /**
     * Find by id.
     *
     * @param id the entity id to search in DB
     * @return the Gift Certificate entity wrapped in an Optional, can be empty if certificate is not found
     */
    Optional<T> findById(long id);
}
