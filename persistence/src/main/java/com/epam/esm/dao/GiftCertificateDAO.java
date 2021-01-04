package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.SqlRequest;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate dao.
 * The interface, defines specific operations for working with Gift Certificate entity in the DB table.
 */
public interface GiftCertificateDAO extends BaseDao<GiftCertificate> {
    /**
     * Update Gift Certificate entity in DB.
     *
     * @param giftCertificate the Gift Certificate entity
     * @return the modified Gift Certificate entity wrapped in an Optional, can be empty if certificate is not found
     */
    Optional<GiftCertificate> update(GiftCertificate giftCertificate);

    /**
     * Find Gift Certificates by parameters.
     *
     * @param sqlRequest the sql request instance
     * @return the list of Gift Certificate entities from DB
     */
    List<GiftCertificate> findAll(SqlRequest sqlRequest);
}
