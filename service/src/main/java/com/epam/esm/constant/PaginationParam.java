package com.epam.esm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaginationParam {
    MIN_PAGE(1),
    DEFAULT_PAGE(1),
    MIN_LIMIT(10),
    MAX_LIMIT(50),
    DEFAULT_LIMIT(20);

    private final int value;
}
