package com.epam.esm.dao.impl;

import com.epam.esm.constant.SQLQuery;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.TagException;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * The type Tag dao.
 */
@Repository
public class TagDAOImpl implements TagDAO {
    /**
     * The Entity manager.
     */
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Tag add(Tag tag) {
        try {
            entityManager.persist(tag);
        } catch (PersistenceException e) {
            entityManager.clear();
            if (findTagByName(tag.getName())) {
                throw new TagException(ExceptionType.TAG_ALREADY_EXIST);
            } else {
                throw new TagException(ExceptionType.TAG_NOT_ADDED);
            }
        }
        return tag;
    }

    @Override
    public void remove(long id) {
        int count;
        try {
            count = entityManager.createQuery(SQLQuery.REMOVE_TAG_BY_ID.getValue())
                    .setParameter(1, id)
                    .executeUpdate();
        } catch (PersistenceException e) {
            throw new TagException(ExceptionType.TAG_NOT_DELETED);
        }
        if (count == 0) {
            throw new TagException(ExceptionType.TAG_NOT_FOUND, String.valueOf(id));
        }
    }

    @Override
    public Tag findById(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null) {
            throw new TagException(ExceptionType.TAG_NOT_FOUND, String.valueOf(id));
        }
        return tag;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tag> findByName(String query) {
        try {
            return entityManager.createQuery(query)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new TagException(ExceptionType.TAGS_NOT_FOUND);
        }
    }

    @Override
    public Tag findMostPopular() {
        Tag tag = (Tag) entityManager.createNativeQuery(SQLQuery.FIND_MOST_POPULAR.getValue(), Tag.class).getSingleResult();
        if (tag != null) {
            return tag;
        }
        throw new TagException(ExceptionType.TAG_NOT_FOUND);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tag> findAll(int offset, int limit) {
        try {
            return entityManager.createQuery(SQLQuery.FIND_ALL_TAGS.getValue())
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new TagException(ExceptionType.TAGS_NOT_FOUND);
        }
    }

    @Override
    public long defineCount() {
        return (Long) entityManager.createQuery(SQLQuery.FIND_COUNT_TAGS.getValue()).getSingleResult();
    }

    private boolean findTagByName(String name) {
        try {
            return entityManager.createQuery(SQLQuery.FIND_BY_NAME.getValue())
                    .setParameter(1, name)
                    .getSingleResult() != null;
        } catch (PersistenceException e) {
            return false;
        }
    }
}
