package sk.tuke.kpi.kp.kp5.blockpuzzle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Comment;

import java.util.Arrays;
import java.util.List;

@Service
public class CommentServiceRestClient implements CommentService {
    private final String url = "http://localhost:8081/api/comments";

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void addComment(Comment comment) {
        restTemplate.postForEntity(url,comment,String.class);
    }

    @Override
    public List<Comment> getComments(String gameName) {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + gameName, Comment[].class).getBody());
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Reset is not supported on web interface");
    }
}
