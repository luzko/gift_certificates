package com.epam.esm.exception;

public class TagException extends ApplicationException {
    public TagException() {

    }

    public TagException(String message) {
        super(message);
    }

    public TagException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagException(Throwable cause) {
        super(cause);
    }

    public TagException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public TagException(ExceptionType exceptionType, String parameter) {
        super(exceptionType, parameter);
    }
}
