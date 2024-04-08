package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.exception.EntityRegistrationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilmService {

    Film findByTitle(String title);

    boolean existsByTitle(String title);

    Long save(Film film);

    List<Film> findAll();

    Film findById(Long filmId);

    List<Film> searchFilmsByTitle(String title);

    Page<Film> searchFilmsByTitlePageable(String title, int pageNumber, int pageSize);

    Page<Film> searchFilmByDirectorNamePageable(String directorName, int pageNumber, int pageSize);


    Page<Film> findAllPageable(int pageNumber, int pageSize);

}
