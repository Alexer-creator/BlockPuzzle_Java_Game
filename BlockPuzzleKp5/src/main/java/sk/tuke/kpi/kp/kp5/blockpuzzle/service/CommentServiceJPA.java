package sk.tuke.kpi.kp.kp5.blockpuzzle.service;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String gameName) {
        return entityManager.createNamedQuery("Comment.getComments").setParameter("game", gameName).setMaxResults(10).getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNamedQuery("Score.resetScores").executeUpdate();
    }
}
