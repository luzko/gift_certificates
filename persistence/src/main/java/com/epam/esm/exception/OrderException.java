package com.epam.esm.exception;

public class OrderException extends ApplicationException {
    public OrderException() {

    }

    public OrderException(String message) {
        super(message);
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderException(Throwable cause) {
        super(cause);
    }

    public OrderException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public OrderException(ExceptionType exceptionType, String parameter) {
        super(exceptionType, parameter);
    }
}
