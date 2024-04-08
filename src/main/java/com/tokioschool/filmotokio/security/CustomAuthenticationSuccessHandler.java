package com.tokioschool.filmotokio.security;

import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.mapper.Mapper;
import com.tokioschool.filmotokio.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {

    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    private final HttpSession httpSession;

    private final UserService userService;

    public CustomAuthenticationSuccessHandler(HttpSession httpSession, UserService userService) {
        this.httpSession = httpSession;
        this.userService = userService;

    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        httpSession.setAttribute("message", "Login bem-sucedido!");

        FilmoTokioUserDetails user = (FilmoTokioUserDetails) event.getAuthentication().getPrincipal();
        User loggedUser = Mapper.parseObject(userService.findByUsername(user.getUsername()), User.class);

        logger.info("User: {} logado com sucesso -> {}", loggedUser.getUsername(), LocalDateTime.now());

        loggedUser.setLastLogin(LocalDateTime.now());
        userService.updateLoginDate(loggedUser);

    }
}