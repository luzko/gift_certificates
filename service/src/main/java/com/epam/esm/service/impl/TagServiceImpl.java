package com.epam.esm.service.impl;

import com.epam.esm.constant.LinkParam;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.TagException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.PaginationUtil;
import com.epam.esm.validation.AppValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * The type Tag service.
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagMapper tagMapper;
    private final TagDAO tagDAO;
    private final AppValidator validator;
    private final PaginationUtil paginationUtil;

    @Override
    @Transactional
    public TagDTO add(TagDTO tagDTO) {
        validator.validate(tagDTO);
        return tagMapper.toDto(tagDAO.add(tagMapper.toEntity(tagDTO)));
    }

    @Override
    @Transactional
    public void remove(long id) {
        tagDAO.remove(id);
    }

    @Override
    @Transactional(readOnly = true)
    public TagDTO findById(long id) {
        return tagMapper.toDto(tagDAO.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public TagDTO findMostPopular() {
        return tagMapper.toDto(tagDAO.findMostPopular());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> findAll(Map<String, String> parameters) {
        int limit = paginationUtil.defineLimit(parameters.get(LinkParam.LIMIT.getValue()));
        int offset = paginationUtil.defineOffset(parameters.get(LinkParam.PAGE.getValue()), limit);
        List<TagDTO> tagDTOs = tagMapper.toDto(tagDAO.findAll(offset, limit));
        if (!tagDTOs.isEmpty()) {
            return tagDTOs;
        }
        throw new TagException(ExceptionType.TAGS_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public long defineCount() {
        return tagDAO.defineCount();
    }
}
