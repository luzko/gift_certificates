package com.epam.esm.exception;

public class PaginationException extends ApplicationException {
    public PaginationException() {

    }

    public PaginationException(String message) {
        super(message);
    }

    public PaginationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaginationException(Throwable cause) {
        super(cause);
    }

    public PaginationException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public PaginationException(ExceptionType exceptionType, String parameter) {
        super(exceptionType, parameter);
    }
}
