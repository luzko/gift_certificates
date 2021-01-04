package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GiftCertificateMapperImpl implements GiftCertificateMapper {
    private final ModelMapper modelMapper;

    @Override
    public GiftCertificate toEntity(GiftCertificateDTO giftCertificateDTO) {
        return Objects.isNull(giftCertificateDTO) ? null : modelMapper.map(giftCertificateDTO, GiftCertificate.class);
    }

    @Override
    public GiftCertificateDTO toDto(GiftCertificate giftCertificate, List<TagDTO> tags) {
        GiftCertificateDTO giftCertificateDTO = Objects.isNull(giftCertificate) ?
                null : modelMapper.map(giftCertificate, GiftCertificateDTO.class);
        if (giftCertificateDTO != null) {
            giftCertificateDTO.setTags(tags);
        }
        return giftCertificateDTO;
    }
}