package com.ansysan.register_of_characteristics.controller;

import com.ansysan.register_of_characteristics.entity.ErrorResponse;
import com.ansysan.register_of_characteristics.exception.DataValidationException;
import com.ansysan.register_of_characteristics.exception.NotFoundException;
import com.ansysan.register_of_characteristics.exception.ServerErrorException;
import com.ansysan.register_of_characteristics.exception.UsernameAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
@Slf4j
public class ControllerErrorHandler {

    @ExceptionHandler(DataValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataValidationException(DataValidationException e, HttpServletRequest request) {
        log.error("Data validation error: {}", e.getMessage());
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        log.error("Not Found validation error: {}", e.getMessage());
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerServerErrorException(ServerErrorException e, HttpServletRequest request) {
        log.error("Server validation error: {}", e.getMessage());
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerUsernameAlreadyExistsException(UsernameAlreadyExistsException e, HttpServletRequest request) {
        log.error("Username not hound error: {}", e.getMessage());
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse buildErrorResponse(Exception e, HttpServletRequest request, HttpStatus status) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .url(request.getRequestURI())
                .status(status)
                .message(e.getMessage())
                .build();
    }
}
