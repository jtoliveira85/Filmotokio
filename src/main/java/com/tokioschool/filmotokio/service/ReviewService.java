package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Review;

import java.util.List;

public interface ReviewService {

    Review findByFilmIdAndUserId(Long filmId, Long userId);

    List<Review> findAllByFilmId(Long filmId);

    boolean save(Review review);

    List<Review> findAll();

    List<Review> findAllByUserId(Long userId);
}
