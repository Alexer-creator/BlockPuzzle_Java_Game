package sk.tuke.kpi.kp.kp5.blockpuzzle.service;

import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);

    List<Comment> getComments(String gameName);

    void reset();



}
