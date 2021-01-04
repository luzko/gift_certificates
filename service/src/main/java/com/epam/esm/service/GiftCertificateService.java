package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificatePatchDTO;

import java.util.List;
import java.util.Map;

/**
 * The interface Gift certificate service. The interface defines certain operations on certificates.
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {
    /**
     * Replacement of a certificate information by a id.
     *
     * @param id                 the certificate id for replacement information
     * @param giftCertificateDTO the giftCertificateDTO with information for replacement
     * @return the modified certificate
     */
    GiftCertificateDTO update(long id, GiftCertificateDTO giftCertificateDTO);

    /**
     * Partial change of certificate information.
     *
     * @param id                      the certificate id for partial change of certificate information
     * @param giftCertificatePatchDTO the giftCertificatePatchDTO with information for partial change information
     * @return the modified certificate
     */
    GiftCertificateDTO update(long id, GiftCertificatePatchDTO giftCertificatePatchDTO);

    /**
     * Find all certificates.
     *
     * @param parameters the search parameters
     * @return the list of GiftCertificateDTOs
     */
    List<GiftCertificateDTO> findAll(Map<String, String> parameters);
}
