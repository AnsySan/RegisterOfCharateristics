package com.ansysan.register_of_characteristics.exception;

public class ServerErrorException extends RuntimeException{

    public ServerErrorException(String message) {
        super(message);
    }
}