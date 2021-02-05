package com.epam.esm.dao;

import com.epam.esm.model.Order;
import com.epam.esm.model.OrderGiftCertificate;
import com.epam.esm.specification.Specification;

import java.util.List;

/**
 * The interface Order dao.
 * The interface, defines specific operations for working with Order entity in the DB table.
 */
public interface OrderDAO extends BaseDAO<Order> {
    /**
     * Create OrderGiftCertificates in DB.
     *
     * @param orderGiftCertificates the entities to create in the DB
     * @return the created orderGiftCertificates
     */
    List<OrderGiftCertificate> add(List<OrderGiftCertificate> orderGiftCertificates);

    /**
     * Find Orders by parameters.
     *
     * @param specifications the list Specification entity for search in DB
     * @param offset         the offset
     * @param limit          the limit
     * @return the list of Order entities from DB
     */
    List<Order> findAll(List<Specification> specifications, int offset, int limit);

    /**
     * Define count orders from DB.
     *
     * @param specifications the list Specification entity for search in DB
     * @return the count orders from DB
     */
    long defineCount(List<Specification> specifications);
}
