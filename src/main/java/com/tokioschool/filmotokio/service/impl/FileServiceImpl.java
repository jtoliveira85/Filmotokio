package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.dto.CreateUserDTO;
import com.tokioschool.filmotokio.dto.UserDTO;
import com.tokioschool.filmotokio.service.FileService;
import com.tokioschool.filmotokio.util.DirectoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    @Value("${images.upload-user-image.dir}")
    private String uploadUserDir;

    @Value("${images.upload-film-image.dir}")
    private String uploadFilmDir;


    @Override
    public boolean uploadUserFile(MultipartFile file, UserDTO userDto) {
        String userFolder = DirectoryUtil.getUserFolder();

        createUserDirectory(userFolder);

        try {
            Path copyLocation = Paths
                    .get(uploadUserDir
                            + File.separator
                            + userFolder
                            + File.separator
                            + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            String imageStr = file.getOriginalFilename();
            userDto.setImage("/" + userFolder + "/" + imageStr);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createUserDirectory(String userFolder) {
        try {
            Path directoryPath = Paths.get(uploadUserDir + File.separator + userFolder);
            if (Files.notExists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            System.out.println("DIRETORIO CRIADO :::::::::::::::::::::::::::::::::::::");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean uploadFilmFile(MultipartFile file, Film film) {

        String userFolder = DirectoryUtil.getUserFolder();

        createFilmDirectory(userFolder);


        try {
            Path copyLocation = Paths
                    .get(uploadFilmDir
                            + File.separator
                            + userFolder
                            + File.separator
                            + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            String imageStr = file.getOriginalFilename();
            film.setPoster("/" + userFolder + "/" + imageStr);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createFilmDirectory(String userFolder) {
        try {
            Path directoryPath = Paths.get(uploadFilmDir + File.separator + userFolder);
            if (Files.notExists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            System.out.println("DIRETORIO CRIADO :::::::::::::::::::::::::::::::::::::");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
