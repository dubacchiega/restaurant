package com.bacchiega.Restaurant.exception;

public class ClientDuplicateException extends RuntimeException{
    public ClientDuplicateException(String message){
        super(message);
    }
}
