package com.softuni.espresso.tracker.service.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {
    private HttpStatus status;
    private String message;
    private LocalDateTime dateTime;

    public ApiError() {
        this.dateTime = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }
}
