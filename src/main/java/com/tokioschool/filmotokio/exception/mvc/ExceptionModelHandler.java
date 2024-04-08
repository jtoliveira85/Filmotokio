package com.tokioschool.filmotokio.exception.mvc;

import com.tokioschool.filmotokio.exception.EntityRegistrationException;
import com.tokioschool.filmotokio.exception.RequiredObjectsIsNullException;
import com.tokioschool.filmotokio.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;


@ControllerAdvice(basePackages = {"com.tokioschool.filmotokio.controller.mvc"})
public class ExceptionModelHandler {

    private final Logger logger = LoggerFactory.getLogger(ExceptionModelHandler.class);


    @ExceptionHandler(RequiredObjectsIsNullException.class)
    public ModelAndView handleRequiredObjectsIsNullException(HttpServletRequest request, RequiredObjectsIsNullException exception) {

        ModelAndView mav = new ModelAndView("error/error");
        mav.addObject("message", exception.getMessage());
//        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception exception) {

        ModelAndView mav = new ModelAndView("error/error");
        mav.addObject("message", exception.getMessage());
//        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ExceptionHandler(EntityRegistrationException.class)
    public ModelAndView handleEntityRegistrationException(HttpServletRequest request, EntityRegistrationException exception) {
        logger.error("Error: " + exception.getMessage(), exception);

        ModelAndView mav = new ModelAndView("error/error");
        mav.addObject("message", exception.getMessage());
//        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException exception) {
        logger.error("Error: " + exception.getMessage(), exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("message", exception.getMessage());
//        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("error/error");
        return mav;
    }



}

