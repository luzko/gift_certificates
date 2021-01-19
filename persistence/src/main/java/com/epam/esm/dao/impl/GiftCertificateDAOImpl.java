package com.epam.esm.dao.impl;

import com.epam.esm.constant.SQLQuery;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.specification.SearchSpecification;
import com.epam.esm.specification.SortSpecification;
import com.epam.esm.specification.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Gift certificate dao.
 */
@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {
    /**
     * The Entity manager.
     */
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        try {
            return merge(giftCertificate);
        } catch (PersistenceException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_ADDED);
        }
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        try {
            return merge(giftCertificate);
        } catch (PersistenceException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_UPDATED);
        }
    }

    @Override
    public void remove(long id) {
        int count;
        try {
            count = entityManager.createQuery(SQLQuery.REMOVE_CERTIFICATE_BY_ID.getValue())
                    .setParameter(1, id)
                    .executeUpdate();
        } catch (PersistenceException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_DELETED);
        }
        if (count == 0) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATES_NOT_FOUND, String.valueOf(id));
        }
    }

    public GiftCertificate findById(long id) {
        try {
            Object giftCertificate = entityManager.createQuery(SQLQuery.FIND_CERTIFICATE_BY_ID.getValue())
                    .setParameter(1, id)
                    .getSingleResult();
            if (giftCertificate == null) {
                throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_FOUND, String.valueOf(id));
            }
            return (GiftCertificate) giftCertificate;
        } catch (PersistenceException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_FOUND, String.valueOf(id));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Long, GiftCertificate> findById(String query) {
        List<GiftCertificate> certificates;
        try {
            certificates = entityManager.createQuery(query).getResultList();
        } catch (PersistenceException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATES_NOT_FOUND);
        }
        return certificates.stream().collect(Collectors.toMap(GiftCertificate::getId, Function.identity()));
    }

    @Override
    public List<GiftCertificate> findAll(List<Specification> specifications, int offset, int limit) {
        try {
            CriteriaQuery<GiftCertificate> criteriaQuery = buildCriteriaQuery(specifications);
            return entityManager.createQuery(criteriaQuery)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATES_NOT_FOUND);
        }
    }

    @Override
    public long defineCount(List<Specification> specifications) {
        CriteriaQuery<GiftCertificate> criteriaQuery = buildCriteriaQuery(specifications);
        return entityManager.createQuery(criteriaQuery).getResultStream().count();
    }

    private GiftCertificate merge(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    private CriteriaQuery<GiftCertificate> buildCriteriaQuery(List<Specification> specifications) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        specifications.forEach(specification -> {
            if (specification instanceof SearchSpecification) {
                predicates.add(((SearchSpecification) specification).toPredicate(criteriaBuilder, root));
            } else if (specification instanceof SortSpecification) {
                orders.add(((SortSpecification) specification).toOrder(criteriaBuilder, root));
            }
        });
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.orderBy(orders.toArray(new Order[0]));
        return criteriaQuery;
    }
}
