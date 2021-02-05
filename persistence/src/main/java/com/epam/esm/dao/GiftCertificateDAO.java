package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.specification.Specification;

import java.util.List;
import java.util.Map;

/**
 * The interface Gift certificate dao.
 * The interface, defines specific operations for working with Gift Certificate entity in the DB table.
 */
public interface GiftCertificateDAO extends BaseDAO<GiftCertificate> {
    /**
     * Update Gift Certificate entity in DB.
     *
     * @param giftCertificate the Gift Certificate entity
     * @return the modified Gift Certificate entity
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Find by id.
     *
     * @param query the String query to search in DB
     * @return the Gift Certificate entity
     */
    Map<Long, GiftCertificate> findById(String query);

    /**
     * Find Gift Certificates by parameters.
     *
     * @param specifications the list Specification entity for search in DB
     * @param offset         the offset count
     * @param limit          the limit count
     * @return the list of Gift Certificate entities from DB
     */
    List<GiftCertificate> findAll(List<Specification> specifications, int offset, int limit);

    /**
     * Define count certificates from DB.
     *
     * @param specifications the list Specification entity for search in DB
     * @return the count certificates from DB
     */
    long defineCount(List<Specification> specifications);
}
