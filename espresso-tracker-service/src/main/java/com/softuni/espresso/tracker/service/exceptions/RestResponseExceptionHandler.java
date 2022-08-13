package com.softuni.espresso.tracker.service.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.enterprise.context.ContextException;
import javax.validation.ValidationException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            UserUnauthorizedException.class,
            UsernameNotFoundException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<ApiError> handleUnauthorized(RuntimeException ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    @ExceptionHandler({UserException.class, ContextException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<ApiError> handleConflict(RuntimeException ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiError> handleBadRequest(RuntimeException ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        if (errors.isEmpty()) {
            return ResponseEntity.ok("");
        }
        String defaultMessage = errors.get(0).getDefaultMessage();
        log.error("Exception: {}, default: {}", ex.getMessage(), defaultMessage,  ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST, defaultMessage));
    }

}
