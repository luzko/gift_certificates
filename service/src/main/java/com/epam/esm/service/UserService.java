package com.epam.esm.service;

import com.epam.esm.dto.UserDTO;

import java.util.List;
import java.util.Map;

/**
 * The interface User service.
 * The interface defines certain operations on users.
 */
public interface UserService {
    /**
     * Find user by id.
     *
     * @param id the id to find the user
     * @return the usersDTO
     */
    UserDTO findById(long id);

    /**
     * Find user by email.
     *
     * @param email the email to find the user
     * @return the usersDTO
     */
    UserDTO findByEmail(String email);

    /**
     * Find all users.
     *
     * @param parameters the parameters
     * @return the list of UserDTO
     */
    List<UserDTO> findAll(Map<String, String> parameters);

    /**
     * Define count users from DB.
     *
     * @return the count users from DB
     */
    long defineCount();
}
