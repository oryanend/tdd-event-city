package com.devsuperior.demo.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
