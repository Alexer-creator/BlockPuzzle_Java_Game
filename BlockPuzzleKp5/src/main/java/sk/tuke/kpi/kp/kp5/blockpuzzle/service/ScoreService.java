package sk.tuke.kpi.kp.kp5.blockpuzzle.service;

import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score);

    List<Score> getTopScores(String game);

    void resetHof();
}
