package com.epam.esm.util.builder.impl;

import com.epam.esm.constant.LinkParam;
import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.mapper.GiftCertificateMapper;
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
public class GiftCertificateHateoasBuilder implements HateoasBuilder<GiftCertificateDTO> {
    private final PaginationLink paginationLink;
    private final GiftCertificateMapper giftCertificateMapper;

    @Override
    public GiftCertificateDTO addLinks(GiftCertificateDTO giftCertificateDTO) {
        long id = giftCertificateDTO.getId();
        giftCertificateDTO.add(linkTo(methodOn(GiftCertificateController.class).findById(id))
                .withSelfRel().withType(LinkParam.GET.getValue()));
        giftCertificateDTO.add(linkTo(methodOn(GiftCertificateController.class).remove(id))
                .withRel(LinkParam.REMOVE.getValue()).withType(LinkParam.DELETE.getValue()));
        giftCertificateDTO.add(linkTo(methodOn(GiftCertificateController.class).update(id, giftCertificateDTO))
                .withRel(LinkParam.UPDATE.getValue()).withType(LinkParam.PUT.getValue()));
        giftCertificateDTO.add(linkTo(methodOn(GiftCertificateController.class).update(
                id, giftCertificateMapper.toPatchDto(giftCertificateDTO)))
                .withRel(LinkParam.UPDATE.getValue()).withType(LinkParam.PATCH.getValue()));
        return giftCertificateDTO;
    }

    @Override
    public RepresentationModel<GiftCertificateDTO> addLinks(List<GiftCertificateDTO> giftCertificateDTOs,
                                                            Map<String, String> parameters, long count) {
        giftCertificateDTOs.forEach(this::addLinks);
        Map<String, Long> info = paginationLink.pageInfo(parameters, count);
        List<Link> links = paginationLink.pageLink(methodOn(GiftCertificateController.class)
                .findAll(parameters), parameters, count);
        CollectionModel<GiftCertificateDTO> certificates = CollectionModel.of(giftCertificateDTOs);
        return buildRepresentationModel(certificates, links, info);
    }
}
