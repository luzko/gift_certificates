package com.epam.esm.service;

/**
 * The interface Base service. General interface, defines basic operations on objects.
 *
 * @param <T> the entity type parameter
 */
public interface BaseService<T> {
    /**
     * Create entity.
     *
     * @param t the entity to create
     * @return the created entity
     */
    T add(T t);

    /**
     * Remove entity by id.
     *
     * @param id the id to delete an entity
     */
    void remove(long id);

    /**
     * Find entity by id.
     *
     * @param id the id to find the entity
     * @return the entity
     */
    T findById(long id);
}
