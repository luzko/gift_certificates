package com.epam.esm.dao.impl;

import com.epam.esm.constant.SQLQuery;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.UserException;
import com.epam.esm.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * The type User dao.
 */
@Repository
public class UserDAOImpl implements UserDAO {
    /**
     * The Entity manager.
     */
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public User findById(long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new UserException(ExceptionType.USER_NOT_FOUND, String.valueOf(id));
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        try {
            Object user = entityManager.createQuery(SQLQuery.FIND_BY_EMAIL.getValue())
                    .setParameter(1, email)
                    .getSingleResult();
            if (user == null) {
                throw new UserException(ExceptionType.USER_NOT_FOUND);
            }
            return (User) user;
        } catch (PersistenceException e) {
            throw new UserException(ExceptionType.USER_NOT_FOUND);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll(int offset, int limit) {
        try {
            return entityManager.createQuery(SQLQuery.FIND_ALL_USERS.getValue())
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new UserException(ExceptionType.USERS_NOT_FOUND);
        }
    }

    @Override
    public long defineCount() {
        return (Long) entityManager.createQuery(SQLQuery.FIND_COUNT_USERS.getValue()).getSingleResult();
    }
}
