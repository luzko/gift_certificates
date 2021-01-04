package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.SqlRequest;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.model.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.SqlRequestGenerator;
import com.epam.esm.validation.AppValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;
    private final GiftCertificateDAO giftCertificateDAO;
    private final GiftCertificateTagDAO giftCertificateTagDAO;
    private final TagDAO tagDAO;
    private final SqlRequestGenerator sqlRequestGenerator;
    private final AppValidator validator;

    @Override
    @Transactional
    public GiftCertificateDTO add(GiftCertificateDTO giftCertificateDTO) {
        validator.validate(giftCertificateDTO);
        GiftCertificate giftCertificate = giftCertificateMapper.toEntity(giftCertificateDTO);
        giftCertificate.setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        giftCertificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        GiftCertificate certificate = giftCertificateDAO.add(giftCertificate);
        List<TagDTO> tagDTOs = giftCertificateDTO.getTags();
        if (tagDTOs != null) {
            insertTags(tagDTOs, certificate);
        }
        List<TagDTO> certificateTags = tagMapper.toDto(tagDAO.findByCertificateId(certificate.getId()));
        return giftCertificateMapper.toDto(certificate, certificateTags);
    }

    @Override
    @Transactional
    public GiftCertificateDTO update(long id, GiftCertificateDTO giftCertificateDTO) {
        validator.validate(giftCertificateDTO);
        GiftCertificate giftCertificate = giftCertificateMapper.toEntity(giftCertificateDTO);
        giftCertificate.setId(id);
        giftCertificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        Optional<GiftCertificate> certificateOptional = giftCertificateDAO.update(giftCertificate);
        if (certificateOptional.isPresent()) {
            List<TagDTO> tagDTOs = giftCertificateDTO.getTags();
            GiftCertificate certificate = certificateOptional.get();
            if (tagDTOs != null) {
                giftCertificateTagDAO.remove(id);
                insertTags(tagDTOs, certificate);
            }
            List<TagDTO> certificateTags = tagMapper.toDto(tagDAO.findByCertificateId(id));
            return giftCertificateMapper.toDto(certificate, certificateTags);
        }
        throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_UPDATED, String.valueOf(id));
    }

    @Override
    @Transactional
    public void remove(long id) {
        if (!giftCertificateDAO.remove(id)) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_DELETED, String.valueOf(id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificateDTO findById(long id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDAO.findById(id);
        if (giftCertificateOptional.isPresent()) {
            return toDto(giftCertificateOptional.get());
        }
        throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_FOUND, String.valueOf(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificateDTO> findAll(Map<String, String> parameters) {
        SqlRequest sqlRequest = sqlRequestGenerator.generateForCertificate(parameters);
        List<GiftCertificate> giftCertificates = giftCertificateDAO.findAll(sqlRequest);
        if (!giftCertificates.isEmpty()) {
            return giftCertificates.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }
        throw new GiftCertificateException(ExceptionType.CERTIFICATES_NOT_FOUND);
    }

    private GiftCertificateDTO toDto(GiftCertificate giftCertificate) {
        List<TagDTO> tagDTOs = tagMapper.toDto(tagDAO.findByCertificateId(giftCertificate.getId()));
        return giftCertificateMapper.toDto(giftCertificate, tagDTOs);
    }

    private void insertTags(List<TagDTO> tagDTOs, GiftCertificate certificate) {
        List<Tag> tagsFromTO = tagMapper.toEntity(tagDTOs);
        SqlRequest sqlRequestAll = sqlRequestGenerator.generateForTag(tagsFromTO);
        List<Tag> tagsExist = tagDAO.findByName(sqlRequestAll);
        tagsFromTO.removeAll(tagsExist);
        if (!tagsFromTO.isEmpty()) {
            tagDAO.add(tagsFromTO);
            SqlRequest sqlRequest = sqlRequestGenerator.generateForTag(tagsFromTO);
            List<Tag> tagsAdded = tagDAO.findByName(sqlRequest);
            tagsExist.addAll(tagsAdded);
        }
        List<GiftCertificateTag> giftCertificateTags = new ArrayList<>();
        for (Tag tag : tagsExist) {
            giftCertificateTags.add(new GiftCertificateTag(certificate.getId(), tag.getId()));
        }
        giftCertificateTagDAO.add(giftCertificateTags);
    }
}