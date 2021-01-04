package com.epam.esm.exception;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {
    private final ExceptionType exceptionType;
    private String parameter;

    protected ApplicationException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    protected ApplicationException(ExceptionType exceptionType, String parameter) {
        this.exceptionType = exceptionType;
        this.parameter = parameter;
    }
}