package com.softuni.espresso.tracker.service.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.enterprise.context.ContextException;

@Slf4j
@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<Object> handleUnauthorized(RuntimeException ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UserException.class, ContextException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ApiError(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT);
    }

}
