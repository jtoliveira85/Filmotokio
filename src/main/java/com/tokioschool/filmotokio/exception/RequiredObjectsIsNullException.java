package com.tokioschool.filmotokio.exception;

public class RequiredObjectsIsNullException extends RuntimeException{

    public RequiredObjectsIsNullException(String ex) {
        super(ex);
    }

    public RequiredObjectsIsNullException() {
        super("It is not allowed to persist a null object!");
    }

}
