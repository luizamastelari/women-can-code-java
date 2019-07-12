package com.womencancode.rbac.controller;

import com.womencancode.rbac.exception.DuplicatedKeyException;
import com.womencancode.rbac.exception.EntityNotFoundException;
import com.womencancode.rbac.exception.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DuplicatedKeyException.class)
    public ResponseEntity<Error> duplicatedKeyException(Exception ex) {
        Error error = buildError(HttpStatus.CONFLICT.value(), ex);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Error> entityNotFoundException(Exception ex) {
        Error error = buildError(HttpStatus.NOT_FOUND.value(), ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> defaultException(Exception ex) {
        Error error = buildError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Error buildError(int status, Exception ex) {
        return Error.builder()
                .status(status)
                .message(ex.getMessage())
                .build();
    }
}
