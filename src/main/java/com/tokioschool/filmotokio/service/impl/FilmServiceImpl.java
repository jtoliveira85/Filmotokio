package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.exception.EntityRegistrationException;
import com.tokioschool.filmotokio.exception.RequiredObjectsIsNullException;
import com.tokioschool.filmotokio.exception.ResourceNotFoundException;
import com.tokioschool.filmotokio.repository.FilmRepository;
import com.tokioschool.filmotokio.security.SecurityUtils;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {


    private final FilmRepository filmRepository;

    private final UserService userService;

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository, UserService userService) {
        this.filmRepository = filmRepository;
        this.userService = userService;
    }


    @Override
    public Film findByTitle(String title) {
        return filmRepository.findByTitle(title);
    }

    @Override
    public boolean existsByTitle(String title) {
        return filmRepository.existsByTitle(title);
    }

    @Override
    public Long save(Film film) {

        if (film == null) {
            throw new RequiredObjectsIsNullException("O objeto Film é obrigatório e não pode ser nulo.");
        }

        Long userId = SecurityUtils.getLoggedUserId();
        User userLoged = userService.findById(userId);

        film.setUser(userLoged);
        film.setMigrate(false);

        Film savedFilm = filmRepository.save(film);

        if (savedFilm.getId() != null) {
            return savedFilm.getId();
        } else {
            throw new EntityRegistrationException("Erro ao Registar o Filme");
        }
    }

    @Override
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public Film findById(Long filmId) {

        Optional<Film> result = filmRepository.findById(filmId);

        Film film = null;
        if (result.isPresent()) {
            film = result.get();
        } else {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        return film;
    }

    @Override
    public List<Film> searchFilmsByTitle(String title) {

        return filmRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public Page<Film> searchFilmsByTitlePageable(String title, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return filmRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Override
    public Page<Film> searchFilmByDirectorNamePageable(String directorName, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return filmRepository.findByDiretorNameContainingIgnoreCaseOrDiretorSurnameContainingIgnoreCase(directorName, pageable);
    }


    @Override
    public Page<Film> findAllPageable(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return filmRepository.findAll(pageable);
    }
}
