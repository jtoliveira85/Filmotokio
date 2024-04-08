package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Score;

import java.util.List;

public interface ScoreService {

    Score findScoreByFilmIdAndUserId(Long filmId, Long userId);

    List<Score> findAllByFilmId(Long filmId);

    Double getScoreByFilmId(Long filmId);

    boolean save(Score score);

}
