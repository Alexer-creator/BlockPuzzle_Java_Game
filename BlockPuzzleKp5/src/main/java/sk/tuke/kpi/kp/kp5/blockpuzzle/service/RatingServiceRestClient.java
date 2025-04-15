package sk.tuke.kpi.kp.kp5.blockpuzzle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Rate;

import java.util.Arrays;
import java.util.List;

@Service
public class RatingServiceRestClient implements RatingService {
    private final String baseUrl = "http://localhost:8081/api/rates";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addRate(Rate rate) {
        restTemplate.postForEntity(baseUrl, rate, String.class);
    }

    @Override
    public List<Rate> getTopRates(String gameName) {
        return Arrays.asList(
                restTemplate.getForEntity(baseUrl + "/top/" + gameName, Rate[].class).getBody()
        );
    }

    @Override
    public List<Rate> getWorstRates(String gameName) {
        return Arrays.asList(
                restTemplate.getForEntity(baseUrl + "/worst/" + gameName, Rate[].class).getBody()
        );
    }

    @Override
    public void resetRates() {
        throw new UnsupportedOperationException("ResetRates is not supported on web interface");
    }
}
