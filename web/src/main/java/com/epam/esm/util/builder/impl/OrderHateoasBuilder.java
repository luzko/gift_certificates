package com.epam.esm.util.builder.impl;

import com.epam.esm.constant.LinkParam;
import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDTO;
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
public class OrderHateoasBuilder implements HateoasBuilder<OrderDTO> {
    private final PaginationLink paginationLink;

    @Override
    public OrderDTO addLinks(OrderDTO orderDTO) {
        long id = orderDTO.getId();
        orderDTO.add(linkTo(methodOn(OrderController.class).findById(id))
                .withSelfRel().withType(LinkParam.GET.getValue()));
        orderDTO.add(linkTo(methodOn(OrderController.class).remove(id))
                .withRel(LinkParam.REMOVE.getValue()).withType(LinkParam.DELETE.getValue()));
        return orderDTO;
    }

    @Override
    public RepresentationModel<OrderDTO> addLinks(List<OrderDTO> orderDTOs, Map<String, String> parameters, long count) {
        orderDTOs.forEach(this::addLinks);
        Map<String, Long> info = paginationLink.pageInfo(parameters, count);
        List<Link> links = paginationLink.pageLink(methodOn(OrderController.class).findAll(parameters), parameters, count);
        CollectionModel<OrderDTO> orders = CollectionModel.of(orderDTOs);
        return buildRepresentationModel(orders, links, info);
    }
}
