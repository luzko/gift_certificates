package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificateTag;

import java.util.List;

/**
 * The interface Gift certificate tag dao.
 * The interface, defines specific operations for working with Gift Certificate Tag entity in the DB table.
 */
public interface GiftCertificateTagDAO {
    /**
     * Create Gift Certificate Tag entity in DB.
     *
     * @param giftCertificateTags the list of Gift Certificate Tag entities to add
     */
    void add(List<GiftCertificateTag> giftCertificateTags);

    /**
     * Remove Gift Certificate Tag by id from DB.
     *
     * @param certificateId the certificate id value to remove
     */
    void remove(long certificateId);
}
