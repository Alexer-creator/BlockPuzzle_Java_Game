package sk.tuke.kpi.kp.kp5.blockpuzzle.service;

import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Rate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addRate(Rate rate) {
        entityManager.persist(rate);
    }

    @Override
    public List<Rate> getTopRates(String gameName) {
        return entityManager.createNamedQuery("Rate.getTopRates", Rate.class).setParameter("game", gameName).setMaxResults(10).getResultList();
    }

    @Override
    public List<Rate> getWorstRates(String gameName) {
        return entityManager.createNamedQuery("Rate.getWorstRates", Rate.class).setParameter("game", gameName).setMaxResults(10).getResultList();
    }

    @Override
    public void resetRates() {
        entityManager.createNamedQuery("Rate.resetRates").executeUpdate();
    }
}
