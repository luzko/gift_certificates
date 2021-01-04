package com.epam.esm.exception;

public class TagException extends ApplicationException {

    public TagException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public TagException(ExceptionType exceptionType, String parameter) {
        super(exceptionType, parameter);
    }
}
