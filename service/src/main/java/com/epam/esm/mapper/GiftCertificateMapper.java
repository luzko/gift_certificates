package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificateOrderDTO;
import com.epam.esm.dto.GiftCertificatePatchDTO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.OrderGiftCertificate;

import java.util.List;

public interface GiftCertificateMapper {
    GiftCertificate toEntity(GiftCertificateDTO giftCertificateDTO);

    GiftCertificateDTO toDto(GiftCertificate giftCertificate);

    GiftCertificateDTO toDto(GiftCertificatePatchDTO giftCertificatePatchDTO);

    GiftCertificatePatchDTO toPatchDto(GiftCertificateDTO giftCertificateDTO);

    List<GiftCertificateDTO> toDto(List<GiftCertificate> giftCertificates);

    List<GiftCertificateOrderDTO> toOrderDto(List<OrderGiftCertificate> orderGiftCertificates);
}
