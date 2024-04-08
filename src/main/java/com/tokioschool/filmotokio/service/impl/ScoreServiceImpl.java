package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Score;
import com.tokioschool.filmotokio.exception.ResourceNotFoundException;
import com.tokioschool.filmotokio.repository.ScoreRepository;
import com.tokioschool.filmotokio.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public Score findScoreByFilmIdAndUserId(Long filmId, Long userId) {

        Score score = scoreRepository.findByFilmIdAndUserId(filmId, userId);

        return score;
    }

    @Override
    public List<Score> findAllByFilmId(Long filmId) {
        return scoreRepository.findAllByFilmId(filmId);
    }

    @Override
    public Double getScoreByFilmId(Long filmId) {

        List<Score> scores = scoreRepository.findAllByFilmId(filmId);

        if (scores.isEmpty()) {
            return 0.0;
        }

        Double totalScore = 0.0;

        for (Score score : scores) {
            totalScore += score.getValue();
        }

        return totalScore / scores.size();
    }

    @Override
    public boolean save(Score score) {

        if (score != null) {
            scoreRepository.save(score);
            return true;
        }

        return false;
    }


}
