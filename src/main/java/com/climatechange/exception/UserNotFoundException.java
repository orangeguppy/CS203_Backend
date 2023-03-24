package com.climatechange.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Long id) {
        super("User with ID " + id + " cannot be found.");
    }
}