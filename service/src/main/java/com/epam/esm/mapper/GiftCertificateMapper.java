package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.GiftCertificate;

import java.util.List;

public interface GiftCertificateMapper {
    GiftCertificate toEntity(GiftCertificateDTO giftCertificateDTO);

    GiftCertificateDTO toDto(GiftCertificate giftCertificate, List<TagDTO> tags);
}