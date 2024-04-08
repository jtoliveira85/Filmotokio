package com.tokioschool.filmotokio.security;

import com.tokioschool.filmotokio.domain.User;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtils {


    public static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public static FilmoTokioUserDetails getLoggedUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof FilmoTokioUserDetails) {
            return (FilmoTokioUserDetails) authentication.getPrincipal();
        }

        return null; // ou lançar uma exceção, dependendo do comportamento desejado
    }

    public static Long getLoggedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof FilmoTokioUserDetails) {
            return ((FilmoTokioUserDetails) authentication.getPrincipal()).getUserId();
        }
        return null; // ou lançar uma exceção, dependendo do comportamento desejado
    }
}
