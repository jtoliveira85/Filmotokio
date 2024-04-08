package com.tokioschool.filmotokio.util;

import com.tokioschool.filmotokio.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;

public class DirectoryUtil {

    public static final String IMAGE_USER_DIR = "images/users/img-uploads";
    public static final String IMAGE_FILM_DIR = "images/film/img-uploads" ;



    public static String getUserFolder() {

        Long userId = SecurityUtils.getLoggedUserId();

        if (userId == null) {
            return "simpleuser";
        } else {
            return "user" + userId;
        }
    }

    public static Long getUniqueFolderId() {
        return System.currentTimeMillis();
    }

}
