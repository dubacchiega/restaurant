package com.bacchiega.Restaurant.exception;

public class OrderEmptyException extends RuntimeException{
    public OrderEmptyException(String message) {
        super(message);
    }
}
