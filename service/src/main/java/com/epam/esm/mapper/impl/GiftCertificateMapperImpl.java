package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificateOrderDTO;
import com.epam.esm.dto.GiftCertificatePatchDTO;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.OrderGiftCertificate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GiftCertificateMapperImpl implements GiftCertificateMapper {
    private final ModelMapper modelMapper;

    @Override
    public GiftCertificate toEntity(GiftCertificateDTO giftCertificateDTO) {
        return Objects.isNull(giftCertificateDTO) ? null : modelMapper.map(giftCertificateDTO, GiftCertificate.class);
    }

    @Override
    public GiftCertificateDTO toDto(GiftCertificate giftCertificate) {
        return Objects.isNull(giftCertificate) ? null : modelMapper.map(giftCertificate, GiftCertificateDTO.class);
    }

    @Override
    public GiftCertificateDTO toDto(GiftCertificatePatchDTO giftCertificatePatchDTO) {
        return Objects.isNull(giftCertificatePatchDTO) ?
                null : modelMapper.map(giftCertificatePatchDTO, GiftCertificateDTO.class);
    }

    @Override
    public GiftCertificatePatchDTO toPatchDto(GiftCertificateDTO giftCertificateDTO) {
        return Objects.isNull(giftCertificateDTO) ?
                null : modelMapper.map(giftCertificateDTO, GiftCertificatePatchDTO.class);
    }

    @Override
    public List<GiftCertificateDTO> toDto(List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateOrderDTO> toOrderDto(List<OrderGiftCertificate> orderGiftCertificates) {
        return orderGiftCertificates.stream()
                .map(this::toOrderDto)
                .collect(Collectors.toList());
    }

    private GiftCertificateOrderDTO toOrderDto(OrderGiftCertificate orderGiftCertificate) {
        GiftCertificate giftCertificate = orderGiftCertificate.getGiftCertificate();
        int count = orderGiftCertificate.getCount();
        GiftCertificateOrderDTO giftCertificateOrderDTO = Objects.isNull(giftCertificate) ?
                null : modelMapper.map(giftCertificate, GiftCertificateOrderDTO.class);
        if (giftCertificateOrderDTO != null) {
            giftCertificateOrderDTO.setCount(count);
        }
        return giftCertificateOrderDTO;
    }
}
