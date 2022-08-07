package com.softuni.espresso.tracker.service.exceptions;

public class UserUnauthorizedException extends RuntimeException {

    public UserUnauthorizedException(String message) {
        super(message);
    }

}
