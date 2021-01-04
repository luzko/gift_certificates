package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.Tag;

import java.util.List;

public interface TagMapper {
    Tag toEntity(TagDTO tagDTO);

    TagDTO toDto(Tag tag);

    List<Tag> toEntity(List<TagDTO> tagDTOs);

    List<TagDTO> toDto(List<Tag> tags);
}
