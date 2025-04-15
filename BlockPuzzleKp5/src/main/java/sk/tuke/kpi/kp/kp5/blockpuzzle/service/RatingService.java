package sk.tuke.kpi.kp.kp5.blockpuzzle.service;

import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Rate;

import java.util.List;

public interface RatingService {
    void addRate(Rate rate);

    List<Rate> getTopRates(String gameName);

    List<Rate> getWorstRates(String gameName);

    void resetRates();
}
