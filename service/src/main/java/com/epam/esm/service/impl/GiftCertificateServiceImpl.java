package com.epam.esm.service.impl;

import com.epam.esm.constant.LinkParam;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificatePatchDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.specification.Specification;
import com.epam.esm.utils.PaginationUtil;
import com.epam.esm.utils.generator.CertificateQueryGenerator;
import com.epam.esm.utils.generator.TagQueryGenerator;
import com.epam.esm.validation.AppValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The type Gift certificate service.
 */
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;
    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;
    private final CertificateQueryGenerator certificateQueryGenerator;
    private final TagQueryGenerator tagQueryGenerator;
    private final AppValidator validator;
    private final PaginationUtil paginationUtil;

    @Override
    @Transactional
    public GiftCertificateDTO add(GiftCertificateDTO giftCertificateDTO) {
        validator.validate(giftCertificateDTO);
        findExistTags(giftCertificateDTO);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        giftCertificateDTO.setCreateDate(zonedDateTime);
        giftCertificateDTO.setLastUpdateDate(zonedDateTime);
        GiftCertificate certificate = giftCertificateDAO.add(giftCertificateMapper.toEntity(giftCertificateDTO));
        return giftCertificateMapper.toDto(certificate);
    }

    @Override
    @Transactional
    public GiftCertificateDTO update(long id, GiftCertificateDTO giftCertificateDTO) {
        validator.validate(giftCertificateDTO);
        GiftCertificate giftCertificate = giftCertificateDAO.findById(id);
        findExistTags(giftCertificateDTO);
        setParameters(id, giftCertificateDTO, giftCertificate.getCreateDate());
        return updateGiftCertificate(giftCertificateDTO);
    }

    @Override
    @Transactional
    public GiftCertificateDTO update(long id, GiftCertificatePatchDTO giftCertificatePatchDTO) {
        validator.validate(giftCertificatePatchDTO);
        GiftCertificate giftCertificate = giftCertificateDAO.findById(id);
        GiftCertificateDTO giftCertificateDTO = giftCertificateMapper.toDto(giftCertificatePatchDTO);
        findExistTags(giftCertificateDTO);
        setParameters(id, giftCertificateDTO, giftCertificate.getCreateDate());
        defineParameters(giftCertificateDTO, giftCertificate);
        return updateGiftCertificate(giftCertificateDTO);
    }

    @Override
    @Transactional
    public void remove(long id) {
        giftCertificateDAO.remove(id);
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificateDTO findById(long id) {
        GiftCertificate giftCertificate = giftCertificateDAO.findById(id);
        return giftCertificateMapper.toDto(giftCertificate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificateDTO> findAll(Map<String, String> parameters) {
        List<Specification> specifications = certificateQueryGenerator.generate(parameters);
        int limit = paginationUtil.defineLimit(parameters.get(LinkParam.LIMIT.getValue()));
        int offset = paginationUtil.defineOffset(parameters.get(LinkParam.PAGE.getValue()), limit);
        List<GiftCertificate> certificates = giftCertificateDAO.findAll(specifications, offset, limit);
        if (!certificates.isEmpty()) {
            return giftCertificateMapper.toDto(certificates);
        }
        throw new GiftCertificateException(ExceptionType.CERTIFICATES_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public long defineCount(Map<String, String> parameters) {
        List<Specification> specifications = certificateQueryGenerator.generate(parameters);
        return giftCertificateDAO.defineCount(specifications);
    }

    private void findExistTags(GiftCertificateDTO giftCertificateDTO) {
        Set<TagDTO> tagDTOs = giftCertificateDTO.getTags();
        String query = tagQueryGenerator.generate(tagDTOs);
        Set<TagDTO> tags = tagMapper.toDto((Collection<Tag>) tagDAO.findByName(query));
        tags.addAll(tagDTOs);
        giftCertificateDTO.setTags(tags);
    }

    private void setParameters(long id, GiftCertificateDTO giftCertificateDTO, ZonedDateTime createDate) {
        giftCertificateDTO.setId(id);
        giftCertificateDTO.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        giftCertificateDTO.setCreateDate(createDate);
    }

    private void defineParameters(GiftCertificateDTO giftCertificateDTO, GiftCertificate giftCertificate) {
        if (giftCertificateDTO.getName() == null) {
            giftCertificateDTO.setName(giftCertificate.getName());
        }
        if (giftCertificateDTO.getDescription() == null) {
            giftCertificateDTO.setDescription(giftCertificate.getDescription());
        }
        if (giftCertificateDTO.getPrice() == null) {
            giftCertificateDTO.setPrice(giftCertificate.getPrice());
        }
        if (giftCertificateDTO.getDuration() == null) {
            giftCertificateDTO.setDuration(giftCertificate.getDuration());
        }
        if (giftCertificateDTO.getTags() == null) {
            giftCertificateDTO.setTags(tagMapper.toDto(giftCertificate.getTags()));
        }
    }

    private GiftCertificateDTO updateGiftCertificate(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate certificate = giftCertificateDAO.update(giftCertificateMapper.toEntity(giftCertificateDTO));
        return giftCertificateMapper.toDto(certificate);
    }
}
