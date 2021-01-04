package com.epam.esm.controller.handler;

import com.epam.esm.dto.WebExceptionResponse;
import com.epam.esm.exception.ApplicationException;
import com.epam.esm.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<WebExceptionResponse> handleApplicationException(ApplicationException e, Locale locale) {
        String parameter = e.getParameter();
        ExceptionType exceptionType = e.getExceptionType();
        String message = defineMessage(parameter, exceptionType, locale);
        WebExceptionResponse response = new WebExceptionResponse(exceptionType.getErrorCode(), message);
        return new ResponseEntity<>(response, new HttpHeaders(), exceptionType.getHttpStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<WebExceptionResponse> handleNoHandlerFoundException(NoHandlerFoundException e, Locale locale) {
        String errorMessage = messageSource.getMessage("404901", new Object[]{}, locale);
        WebExceptionResponse response = new WebExceptionResponse("404901", errorMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<WebExceptionResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e, Locale locale
    ) {
        String errorMessage = messageSource.getMessage("500001", new Object[]{}, locale);
        WebExceptionResponse response = new WebExceptionResponse("500001", errorMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<WebExceptionResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e, Locale locale
    ) {
        String errorMessage = messageSource.getMessage("500002", new Object[]{}, locale);
        WebExceptionResponse response = new WebExceptionResponse("500002", errorMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<WebExceptionResponse> handleUnsatisfiedServletRequestParameterException(
            UnsatisfiedServletRequestParameterException e, Locale locale
    ) {
        String errorMessage = messageSource.getMessage("500003", new Object[]{}, locale);
        WebExceptionResponse response = new WebExceptionResponse("500003", errorMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<WebExceptionResponse> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException e, Locale locale
    ) {
        String errorMessage = messageSource.getMessage("500004", new Object[]{}, locale);
        WebExceptionResponse response = new WebExceptionResponse("500004", errorMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<WebExceptionResponse> handleHttpMediaTypeNotAcceptableException(
            HttpMediaTypeNotAcceptableException e, Locale locale
    ) {
        String errorMessage = messageSource.getMessage("500005", new Object[]{}, locale);
        WebExceptionResponse response = new WebExceptionResponse("500005", errorMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<WebExceptionResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e, Locale locale
    ) {
        String errorMessage = messageSource.getMessage("500006", new Object[]{}, locale);
        WebExceptionResponse response = new WebExceptionResponse("500006", errorMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<WebExceptionResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e, Locale locale
    ) {
        String errorMessage = messageSource.getMessage("500007", new Object[]{}, locale);
        WebExceptionResponse response = new WebExceptionResponse("500007", errorMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<WebExceptionResponse> handleHttpClientErrorException(
            HttpClientErrorException.BadRequest e, Locale locale
    ) {
        String errorMessage = messageSource.getMessage("500008", new Object[]{}, locale);
        WebExceptionResponse response = new WebExceptionResponse("500008", errorMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<WebExceptionResponse> handleThrowable(Throwable e, Locale locale) {
        String errorMessage = messageSource.getMessage("500100", new Object[]{}, locale);
        WebExceptionResponse response = new WebExceptionResponse("500100", errorMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String defineMessage(String parameter, ExceptionType exceptionType, Locale locale) {
        String errorMessage = messageSource.getMessage(exceptionType.getErrorCode(), new Object[]{}, locale);
        return parameter == null ? errorMessage : errorMessage + String.format(" (id = %s)", parameter);
    }
}