package com.epam.esm.util.builder;

import com.epam.esm.constant.LinkParam;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.hal.HalModelBuilder;

import java.util.List;
import java.util.Map;

public interface HateoasBuilder<T extends RepresentationModel<T>> {
    T addLinks(T t);

    RepresentationModel<T> addLinks(List<T> dto, Map<String, String> parameters, long count);

    default RepresentationModel<T> buildRepresentationModel(Object model, Iterable<Link> links, Object embedded) {
        return HalModelBuilder
                .halModelOf(model)
                .embed(embedded, LinkRelation.of(LinkParam.PAGES_INFO.getValue()))
                .links(links)
                .build();
    }
}
