package com.epam.esm.util;

import com.epam.esm.constant.LinkParam;
import com.epam.esm.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class PaginationLink {
    private final PaginationUtil paginationUtil;

    public Map<String, Long> pageInfo(Map<String, String> parameters, long count) {
        int page = paginationUtil.definePage(parameters.get(LinkParam.PAGE.getValue()));
        int limit = paginationUtil.defineLimit(parameters.get(LinkParam.LIMIT.getValue()));
        Map<String, Long> info = new HashMap<>();
        info.put(LinkParam.COUNT_PAGES.getValue(), definePageCount(count, limit));
        info.put(LinkParam.CURRENT_PAGE.getValue(), (long) page);
        info.put(LinkParam.LIMIT_VALUE.getValue(), (long) limit);
        info.put(LinkParam.COUNT_RECORDS.getValue(), count);
        return info;
    }

    public List<Link> pageLink(Object value, Map<String, String> parameters, long count) {
        List<Link> links = new ArrayList<>();
        int page = paginationUtil.definePage(parameters.get(LinkParam.PAGE.getValue()));
        int limit = paginationUtil.defineLimit(parameters.get(LinkParam.LIMIT.getValue()));
        if (page != 1) {
            parameters.put(LinkParam.PAGE.getValue(), String.valueOf(page - 1));
            parameters.put(LinkParam.LIMIT.getValue(), String.valueOf(limit));
            links.add(linkTo(value).withRel(LinkParam.PREV.getValue()));
        }
        if (page != definePageCount(count, limit)) {
            parameters.put(LinkParam.PAGE.getValue(), String.valueOf(page + 1));
            parameters.put(LinkParam.LIMIT.getValue(), String.valueOf(limit));
            links.add(linkTo(value).withRel(LinkParam.NEXT.getValue()));
        }
        return links;
    }

    private long definePageCount(long count, int limit) {
        return (long) Math.ceil(count / (double) limit);
    }
}
