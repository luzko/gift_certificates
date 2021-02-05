package com.epam.esm.util.builder.impl;

import com.epam.esm.constant.LinkParam;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.util.PaginationLink;
import com.epam.esm.util.builder.HateoasBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class TagHateoasBuilder implements HateoasBuilder<TagDTO> {
    private final PaginationLink paginationLink;

    public TagDTO addLinks(TagDTO tagDTO) {
        long id = tagDTO.getId();
        tagDTO.add(linkTo(methodOn(TagController.class).findById(id))
                .withSelfRel().withType(LinkParam.GET.getValue()));
        tagDTO.add(linkTo(methodOn(TagController.class).remove(id))
                .withRel(LinkParam.REMOVE.getValue()).withType(LinkParam.DELETE.getValue()));
        return tagDTO;
    }

    public RepresentationModel<TagDTO> addLinks(List<TagDTO> tagDTOs, Map<String, String> parameters, long count) {
        tagDTOs.forEach(this::addLinks);
        Map<String, Long> info = paginationLink.pageInfo(parameters, count);
        List<Link> links = paginationLink.pageLink(methodOn(TagController.class).findAll(parameters), parameters, count);
        CollectionModel<TagDTO> tags = CollectionModel.of(tagDTOs);
        return buildRepresentationModel(tags, links, info);
    }
}
