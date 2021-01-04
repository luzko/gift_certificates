package com.epam.esm.exception;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {
    private ExceptionType exceptionType;
    private String parameter;

    public ApplicationException() {

    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public ApplicationException(ExceptionType exceptionType, String parameter) {
        this.exceptionType = exceptionType;
        this.parameter = parameter;
    }
}
