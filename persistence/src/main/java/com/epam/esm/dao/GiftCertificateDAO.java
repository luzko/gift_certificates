package com.epam.esm.dao;

import com.epam.esm.entity.SqlRequest;
import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDAO extends BaseDao<GiftCertificate> {
    Optional<GiftCertificate> update(GiftCertificate giftCertificate);

    List<GiftCertificate> findAll(SqlRequest sqlRequest);
}