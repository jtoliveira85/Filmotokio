package com.tokioschool.filmotokio.controller.mvc;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller

public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Integer status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null && status == HttpStatus.NOT_FOUND.value()) {
            System.out.println("ERRRRRRO: 404");
            return "error/404";
        }

        if (status != null && status == HttpStatus.FORBIDDEN.value()) {
            System.out.println("ERRRRRRO: 403");
            return "error/access-denied";
        }

        System.out.println("ERRRRRRO: OTHER");
        model.addAttribute("message", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        model.addAttribute("url", request.getRequestURL());
        return "error/error";
    }
}
