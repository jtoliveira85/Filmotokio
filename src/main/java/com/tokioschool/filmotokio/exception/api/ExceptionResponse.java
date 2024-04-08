package com.tokioschool.filmotokio.exception.api;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ExceptionResponse {

    private String timestamp;

    private int status;

    private String error;

    private String message;

    private String path;

    public ExceptionResponse(LocalDateTime time, HttpStatus status, String message, String path) {
        this.timestamp = time.toString();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;

    }

}
