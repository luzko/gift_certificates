package com.epam.esm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtParam {
    ROLE("role"),
    AUTHORIZATION("Authorization"),
    BEARER("Bearer_");

    private final String value;
}
