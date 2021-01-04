package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {
    GiftCertificateDTO update(long id, GiftCertificateDTO giftCertificateDTO);

    List<GiftCertificateDTO> findAll(Map<String, String> parameters);
}
