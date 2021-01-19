package com.epam.esm.util.builder.impl;

import com.epam.esm.constant.LinkParam;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDTO;
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
public class UserHateoasBuilder implements HateoasBuilder<UserDTO> {
    private final PaginationLink paginationLink;

    @Override
    public UserDTO addLinks(UserDTO userDTO) {
        userDTO.add(linkTo(methodOn(UserController.class).findById(userDTO.getId()))
                .withSelfRel().withType(LinkParam.GET.getValue()));
        return userDTO;
    }

    @Override
    public RepresentationModel<UserDTO> addLinks(List<UserDTO> userDTOs, Map<String, String> parameters, long count) {
        userDTOs.forEach(this::addLinks);
        Map<String, Long> info = paginationLink.pageInfo(parameters, count);
        List<Link> links = paginationLink.pageLink(methodOn(UserController.class).findAll(parameters), parameters, count);
        CollectionModel<UserDTO> users = CollectionModel.of(userDTOs);
        return buildRepresentationModel(users, links, info);
    }
}
