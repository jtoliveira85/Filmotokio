package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.dto.CreateUserDTO;
import com.tokioschool.filmotokio.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileService {

    boolean uploadUserFile(MultipartFile file, UserDTO userDto);

//    void createUserDirectory(Long userId);

    boolean uploadFilmFile(MultipartFile file, Film film);

//    Path createFilmDirectory(String filmNameForUrl);
}
