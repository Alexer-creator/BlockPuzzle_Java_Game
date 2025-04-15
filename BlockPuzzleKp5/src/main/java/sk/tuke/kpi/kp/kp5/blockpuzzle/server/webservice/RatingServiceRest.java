package sk.tuke.kpi.kp.kp5.blockpuzzle.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Rate;
import sk.tuke.kpi.kp.kp5.blockpuzzle.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/top/{game}")
    public List<Rate> getTopRates(@PathVariable String game) {
        return ratingService.getTopRates(game);
    }

    @GetMapping("/worst/{game}")
    public List<Rate> getWorstRates(@PathVariable String game) {
        return ratingService.getWorstRates(game);
    }

    @PostMapping
    public void addRate(@RequestBody Rate rate) {
        ratingService.addRate(rate);
    }
}
