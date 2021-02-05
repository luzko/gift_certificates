package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum ExceptionType {
    //tag
    TAG_NOT_ADDED("400001"),
    TAG_NOT_DELETED("400002"),
    TAG_ALREADY_EXIST("400004"),
    TAG_NOT_FOUND("404001", HttpStatus.NOT_FOUND),
    TAGS_NOT_FOUND("404002", HttpStatus.NOT_FOUND),

    //tag validation
    TAG_NAME_INVALID("400003"),

    //gift certificate
    CERTIFICATE_NOT_ADDED("400101"),
    CERTIFICATE_NOT_UPDATED("400102"),
    CERTIFICATE_NOT_DELETED("400103"),
    CERTIFICATE_TAG_NOT_ADDED("400104"),
    CERTIFICATE_TAG_NOT_DELETED("400105"),
    CERTIFICATE_NOT_FOUND("404101", HttpStatus.NOT_FOUND),
    CERTIFICATES_NOT_FOUND("404102", HttpStatus.NOT_FOUND),

    //gift certificate validation
    CERTIFICATE_NAME_EMPTY("400106"),
    CERTIFICATE_NAME_INVALID("400107"),
    CERTIFICATE_DESCRIPTION_EMPTY("400108"),
    CERTIFICATE_DESCRIPTION_INVALID("400109"),
    CERTIFICATE_PRICE_EMPTY("400110"),
    CERTIFICATE_PRICE_NEGATIVE("400111"),
    CERTIFICATE_PRICE_EXCEED("400112"),
    CERTIFICATE_DURATION_EMPTY("400113"),
    CERTIFICATE_DURATION_NEGATIVE("400114"),
    CERTIFICATE_DURATION_EXCEED("400115"),
    CERTIFICATE_ID_EMPTY("400116"),
    CERTIFICATE_ID_NEGATIVE("400117"),
    CERTIFICATE_COUNT_EMPTY("400118"),
    CERTIFICATE_COUNT_NEGATIVE("400119"),
    CERTIFICATE_COUNT_EXCEED("400120"),
    CERTIFICATE_DUPLICATION("400121"),

    //user
    USER_NOT_FOUND("404201", HttpStatus.NOT_FOUND),
    USERS_NOT_FOUND("404202", HttpStatus.NOT_FOUND),

    //user validation
    USER_ID_INVALID("400201"),
    USER_ID_NEGATIVE("400202"),

    //order
    ORDER_NOT_ADDED("400301"),
    ORDER_NOT_DELETED("400302"),
    ORDER_NOT_FOUND("404301", HttpStatus.NOT_FOUND),
    ORDERS_NOT_FOUND("404302", HttpStatus.NOT_FOUND),

    //order validation
    ORDER_USER_ID_EMPTY("400303"),
    ORDER_USER_ID_NEGATIVE("400304"),

    //pagination
    PAGE_VALUE_NEGATIVE("400901"),
    PAGE_VALUE_INVALID("400902"),
    LIMIT_VALUE_EXCEED("400903"),
    LIMIT_VALUE_INVALID("400904");

    private final String errorCode;
    private final HttpStatus httpStatus;

    ExceptionType(String errorCode) {
        this.errorCode = errorCode;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public static Optional<ExceptionType> defineTypeByCode(String errorCode) {
        return Arrays.stream(values())
                .filter(type -> type.getErrorCode().equals(errorCode))
                .findAny();
    }
}
