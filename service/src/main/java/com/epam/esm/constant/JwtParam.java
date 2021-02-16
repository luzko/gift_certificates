package com.epam.esm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtParam {
    ROLE("role"),
    AUTHORIZATION("Authorization");

    private final String value;
}
