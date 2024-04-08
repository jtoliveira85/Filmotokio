package com.tokioschool.filmotokio.controller.mvc;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.dto.UserDTO;
import com.tokioschool.filmotokio.security.FilmoTokioUserDetails;
import com.tokioschool.filmotokio.security.SecurityUtils;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.service.UserService;
import com.tokioschool.filmotokio.util.DirectoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class WebController {

        private final FilmService filmService;


    @Autowired
    public WebController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {

        String infoMessage = (String) session.getAttribute("message");
        System.out.println("infoMessage: " + infoMessage);

        if (infoMessage != null) {
            model.addAttribute("message", infoMessage);
        }
        session.removeAttribute("message");

        List<Film> filmList = filmService.findAll();
        model.addAttribute("filmList", filmList);
        model.addAttribute("filmImageURL", DirectoryUtil.IMAGE_FILM_DIR);

        return "home/index";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login-page";
    }

    @GetMapping("/denied")
    public String negado() {
        return "error/access-denied";
    }
}
