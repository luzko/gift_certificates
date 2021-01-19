package com.epam.esm.dao;

/**
 * The interface Base dao. General interface, defines basic operations for working with the DB tables.
 *
 * @param <T> the entity type
 */
public interface BaseDAO<T> {
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
     */
    void remove(long id);

    /**
     * Find by id.
     *
     * @param id the entity id to search in DB
     * @return the Gift Certificate entity
     */
    T findById(long id);
}
