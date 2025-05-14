package com.bacchiega.Restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

//    TODO completar o response status para cada tipo de erro

    @ExceptionHandler(ProductDuplicateException.class)
    public ResponseEntity<Map<String, String>> handleProductDuplicateException(ProductDuplicateException e) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error: ", e.getMessage());
        error.put("message: ", "A product with the provided information already exists. Please verify the data and try again.");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClientDuplicateException.class)
    public ResponseEntity<Map<String, String>> handleClientDuplicateException(ClientDuplicateException e) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error: ", e.getMessage());
        error.put("message: ", "A client with the provided information already exists. Please check the data and try again.");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleClientNotFoundException(ClientNotFoundException e) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error: ", e.getMessage());
        error.put("message: ", "Client not found. Please make sure the client exists and try again.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderEmptyException.class)
    public ResponseEntity<Map<String, String>> handleOrderEmptyException(OrderEmptyException e) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error: ", e.getMessage());
        error.put("message: ", "The order is empty. Please add at least one item before submitting.");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<Map<String, String>> handlePaymentException(PaymentException e) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error: ", e.getMessage());
        error.put("message: ", "A payment error occurred. Please check the provided data and try again.");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException e) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error: ", e.getMessage());
        error.put("message: ", "Product not found. Please verify the product information and try again.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException e) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error: ", e.getMessage());
        error.put("message: ", "Resource not found. Please ensure the resource exists and try again.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(OrderNotFoundException e) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error: ", e.getMessage());
        error.put("message: ", "Order not found. Please verify the order information and try again.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderStatusException.class)
    public ResponseEntity<Map<String, String>> handleOrderStatusException(OrderStatusException e) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error: ", e.getMessage());
        error.put("message: ", "Order status is invalid. Please check the order status and try again.");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
