package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TagMapperImpl implements TagMapper {

    private final ModelMapper modelMapper;

    @Override
    public Tag toEntity(TagDTO tagDTO) {
        return Objects.isNull(tagDTO) ? null : modelMapper.map(tagDTO, Tag.class);
    }

    @Override
    public TagDTO toDto(Tag tag) {
        return Objects.isNull(tag) ? null : modelMapper.map(tag, TagDTO.class);
    }

    @Override
    public List<Tag> toEntity(List<TagDTO> tagDTOs) {
        List<Tag> tags = new ArrayList<>();
        for (TagDTO tagDTO : tagDTOs) {
            tags.add(toEntity(tagDTO));
        }
        return tags;
    }

    @Override
    public List<TagDTO> toDto(List<Tag> tags) {
        List<TagDTO> tagDTOs = new ArrayList<>();
        for (Tag tag : tags) {
            tagDTOs.add(toDto(tag));
        }
        return tagDTOs;
    }
}
