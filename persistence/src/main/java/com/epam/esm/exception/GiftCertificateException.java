package com.epam.esm.exception;

public class GiftCertificateException extends ApplicationException {
    public GiftCertificateException() {

    }

    public GiftCertificateException(String message) {
        super(message);
    }

    public GiftCertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiftCertificateException(Throwable cause) {
        super(cause);
    }

    public GiftCertificateException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public GiftCertificateException(ExceptionType exceptionType, String parameter) {
        super(exceptionType, parameter);
    }
}
