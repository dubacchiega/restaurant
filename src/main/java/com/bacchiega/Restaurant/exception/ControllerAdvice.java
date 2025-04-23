package com.bacchiega.Restaurant.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ProductDuplicateException.class)
    public String handleProductDuplicateException(ProductDuplicateException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ClientDuplicateException.class)
    public String handleClientDuplicateException(ClientDuplicateException e) {
        return e.getMessage();
    }
}
