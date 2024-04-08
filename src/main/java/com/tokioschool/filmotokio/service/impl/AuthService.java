package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.security.FilmoTokioUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public void refreshAuthUser(String userImgStr) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {

            if (auth.getPrincipal() instanceof FilmoTokioUserDetails userDetails) {

                userDetails.setUserImage(userImgStr);
            }
        }
    }
}
