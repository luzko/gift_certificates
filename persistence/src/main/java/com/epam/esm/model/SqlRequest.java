package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SqlRequest {
    private final String sqlQuery;
    private final Object[] requestParameters;
}
