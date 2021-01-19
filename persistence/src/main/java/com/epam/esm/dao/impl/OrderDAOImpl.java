package com.epam.esm.dao.impl;

import com.epam.esm.constant.SQLQuery;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.OrderException;
import com.epam.esm.model.Order;
import com.epam.esm.model.OrderGiftCertificate;
import com.epam.esm.specification.SearchSpecification;
import com.epam.esm.specification.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Order dao.
 */
@Repository
public class OrderDAOImpl implements OrderDAO {
    /**
     * The Entity manager.
     */
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Order add(Order order) {
        try {
            entityManager.persist(order);
        } catch (PersistenceException e) {
            throw new OrderException(ExceptionType.ORDER_NOT_ADDED);
        }
        return order;
    }

    @Override
    public List<OrderGiftCertificate> add(List<OrderGiftCertificate> orderGiftCertificates) {
        try {
            for (OrderGiftCertificate orderGiftCertificate : orderGiftCertificates) {
                entityManager.persist(orderGiftCertificate);
            }
        } catch (PersistenceException e) {
            throw new OrderException(ExceptionType.ORDER_NOT_ADDED);
        }
        return orderGiftCertificates;
    }

    @Override
    public void remove(long id) {
        int count;
        try {
            count = entityManager.createQuery(SQLQuery.REMOVE_ORDER_BY_ID.getValue())
                    .setParameter(1, id)
                    .executeUpdate();
        } catch (PersistenceException e) {
            throw new OrderException(ExceptionType.ORDER_NOT_DELETED);
        }
        if (count == 0) {
            throw new OrderException(ExceptionType.ORDER_NOT_FOUND, String.valueOf(id));
        }
    }

    @Override
    public Order findById(long id) {
        try {
            Object order = entityManager.createQuery(SQLQuery.FIND_ORDER_BY_ID.getValue())
                    .setParameter(1, id)
                    .getSingleResult();
            if (order == null) {
                throw new OrderException(ExceptionType.ORDER_NOT_FOUND, String.valueOf(id));
            }
            return (Order) order;
        } catch (PersistenceException e) {
            throw new OrderException(ExceptionType.ORDER_NOT_FOUND, String.valueOf(id));
        }
    }

    @Override
    public List<Order> findAll(List<Specification> specifications, int offset, int limit) {
        try {
            CriteriaQuery<Order> criteriaQuery = buildCriteriaQuery(specifications);
            return entityManager.createQuery(criteriaQuery)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new OrderException(ExceptionType.ORDERS_NOT_FOUND);
        }
    }

    @Override
    public long defineCount(List<Specification> specifications) {
        CriteriaQuery<Order> criteriaQuery = buildCriteriaQuery(specifications);
        return entityManager.createQuery(criteriaQuery).getResultStream().count();
    }

    private CriteriaQuery<Order> buildCriteriaQuery(List<Specification> specifications) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        List<Predicate> predicates = new ArrayList<>();
        specifications.forEach(specification -> {
            if (specification instanceof SearchSpecification) {
                predicates.add(((SearchSpecification) specification).toPredicate(criteriaBuilder, root));
            }
        });
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return criteriaQuery;
    }
}
