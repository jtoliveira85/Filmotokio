package com.tokioschool.filmotokio.exception.api;


import com.tokioschool.filmotokio.exception.RequiredObjectsIsNullException;
import com.tokioschool.filmotokio.exception.ResourceAlreadyExistsException;
import com.tokioschool.filmotokio.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice(basePackages = {"com.tokioschool.filmotokio.controller.rest"})
//@ControllerAdvice
@RestController
public class ExceptionApiHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handelGenericError(HttpServletRequest request, Exception ex) {
        return buildResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleResourceAlreadyExistsException(HttpServletRequest request, ResourceAlreadyExistsException ex) {
        return buildResponse(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RequiredObjectsIsNullException.class)
    public ResponseEntity<ExceptionResponse> handleRequiredObjectsIsNullException(HttpServletRequest request, RequiredObjectsIsNullException ex) {
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(HttpServletRequest request, RequiredObjectsIsNullException ex) {
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<ExceptionResponse> buildResponse(Exception exception, HttpServletRequest request, HttpStatus status) {
        return ResponseEntity.status(status).body(
                new ExceptionResponse(LocalDateTime.now(), status, exception.getMessage(), request.getRequestURI())
        );
    }

}

