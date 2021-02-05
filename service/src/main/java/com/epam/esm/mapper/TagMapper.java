package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface TagMapper {
    Tag toEntity(TagDTO tagDTO);

    TagDTO toDto(Tag tag);

    List<TagDTO> toDto(List<Tag> tags);

    Set<TagDTO> toDto(Collection<Tag> tags);
}
