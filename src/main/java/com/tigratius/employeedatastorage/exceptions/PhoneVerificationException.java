package com.tigratius.employeedatastorage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PhoneVerificationException extends RuntimeException{
    public PhoneVerificationException(String message) {
        super(message);
    }
}
