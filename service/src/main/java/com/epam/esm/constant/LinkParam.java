package com.epam.esm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LinkParam {
    PAGE("page"),
    LIMIT("limit"),
    COUNT_PAGES("count pages"),
    CURRENT_PAGE("current page"),
    LIMIT_VALUE("limit value"),
    COUNT_RECORDS("count records"),
    PREV("prev"),
    NEXT("next"),
    PAGES_INFO("pages info"),
    REMOVE("remove"),
    UPDATE("update"),
    GET("GET"),
    DELETE("DELETE"),
    PUT("PUT"),
    PATCH("PATCH");

    private final String value;
}
