package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review findByFilmIdAndUserId(Long filmId, Long userId);

    List<Review> findAllByFilmId(Long filmId);

    List<Review> findAllByUserId(Long userId);

}
