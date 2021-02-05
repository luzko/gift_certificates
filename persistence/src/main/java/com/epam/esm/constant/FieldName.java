package com.epam.esm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FieldName {
    TAGS("tags"),
    NAME("name"),
    DESCRIPTION("description"),
    USER_ID("userId"),
    IS_DELETED("isDeleted");

    private final String value;
}
