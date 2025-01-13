package com.nazran.springboot3firebseauth.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityNotFound extends Exception {
    private final int status;
    private final String message;

    public EntityNotFound(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
