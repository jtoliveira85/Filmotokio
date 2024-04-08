package com.tokioschool.filmotokio.exception;

public class EntityRegistrationException extends RuntimeException{

    public EntityRegistrationException() {
        super();
    }

    public EntityRegistrationException(String message) {
        super(message);
    }
}
