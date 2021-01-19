package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    public List<TagDTO> toDto(List<Tag> tags) {
        return tags.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Set<TagDTO> toDto(Collection<Tag> tags) {
        return tags.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}
