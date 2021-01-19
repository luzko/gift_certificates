package com.epam.esm.exception;

public class UserException extends ApplicationException {
    public UserException() {

    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    public UserException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public UserException(ExceptionType exceptionType, String parameter) {
        super(exceptionType, parameter);
    }
}
