package sk.tuke.kpi.kp.kp5.blockpuzzle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Score;

import java.util.Arrays;
import java.util.List;

@Service
public class ScoreServiceRestClient implements ScoreService{
    private String url = "http://localhost:8081/api/score";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(url, score, Score.class);
    }

    @Override
    public List<Score> getTopScores(String game) {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Score[].class).getBody());
    }

    @Override
    public void resetHof() {
        throw new UnsupportedOperationException("ResetHof is not supported on web interface");
    }
}