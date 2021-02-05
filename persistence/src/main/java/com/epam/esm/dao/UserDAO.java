package com.epam.esm.dao;

import com.epam.esm.model.User;

import java.util.List;

/**
 * The interface User dao.
 * The interface, defines specific operations for working with User entity in the DB table.
 */
public interface UserDAO {
    /**
     * Find by id.
     *
     * @param id the user id to search in DB
     * @return the Order entity
     */
    User findById(long id);

    /**
     * Find all users.
     *
     * @param offset the offset count
     * @param limit  the limit count
     * @return the list of Users from DB
     */
    List<User> findAll(int offset, int limit);

    /**
     * Define count users from DB.
     *
     * @return the count users from DB
     */
    long defineCount();
}
