package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificateTag;

import java.util.List;

public interface GiftCertificateTagDAO {
    void add(List<GiftCertificateTag> giftCertificateTags);

    void remove(long certificateId);
}