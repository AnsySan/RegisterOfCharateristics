package com.ansysan.register_of_characteristics.exception;

public class DataValidationException extends RuntimeException{
    public DataValidationException(String message) {
        super(message);
    }
}