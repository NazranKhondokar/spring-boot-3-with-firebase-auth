package com.nazran.springboot3firebseauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmailNotVerifiedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 14L;

    public EmailNotVerifiedException(String s) {
        super(s);
    }

}
