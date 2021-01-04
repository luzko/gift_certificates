package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.TagException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validation.AppValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagServiceImpl implements TagService {
    private final TagMapper tagMapper;
    private final TagDAO tagDAO;
    private final AppValidator validator;

    @Override
    @Transactional
    public TagDTO add(TagDTO tagDTO) {
        validator.validate(tagDTO);
        Tag tag = tagDAO.add(tagMapper.toEntity(tagDTO));
        if (tag.getId() != null) {
            return tagMapper.toDto(tag);
        }
        throw new TagException(ExceptionType.TAG_NOT_ADDED);
    }

    @Override
    @Transactional
    public void remove(long id) {
        if (!tagDAO.remove(id)) {
            throw new TagException(ExceptionType.TAG_NOT_DELETED, String.valueOf(id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TagDTO findById(long id) {
        Optional<Tag> tagOptional = tagDAO.findById(id);
        if (tagOptional.isPresent()) {
            return tagMapper.toDto(tagOptional.get());
        }
        throw new TagException(ExceptionType.TAG_NOT_FOUND, String.valueOf(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> findAll() {
        List<TagDTO> tagDTOs = tagMapper.toDto(tagDAO.findAll());
        if (!tagDTOs.isEmpty()) {
            return tagDTOs;
        }
        throw new TagException(ExceptionType.TAGS_NOT_FOUND);
    }
}