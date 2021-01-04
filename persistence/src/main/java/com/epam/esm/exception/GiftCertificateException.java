package com.epam.esm.exception;

public class GiftCertificateException extends ApplicationException {
    public GiftCertificateException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public GiftCertificateException(ExceptionType exceptionType, String parameter) {
        super(exceptionType, parameter);
    }
}