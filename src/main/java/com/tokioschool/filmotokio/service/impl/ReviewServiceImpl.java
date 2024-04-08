package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Review;
import com.tokioschool.filmotokio.repository.ReviewRepository;
import com.tokioschool.filmotokio.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Review findByFilmIdAndUserId(Long filmId, Long userId) {

        return repository.findByFilmIdAndUserId(filmId, userId);
    }

    @Override
    public List<Review> findAllByFilmId(Long filmId) {

        return repository.findAllByFilmId(filmId);
    }

    @Override
    public boolean save(Review review) {

        if (review != null) {
            repository.save(review);
            return true;
        }

        return false;
    }

    @Override
    public List<Review> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Review> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }
}
