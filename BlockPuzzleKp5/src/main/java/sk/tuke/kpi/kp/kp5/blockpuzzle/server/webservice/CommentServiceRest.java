package sk.tuke.kpi.kp.kp5.blockpuzzle.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Comment;
import sk.tuke.kpi.kp.kp5.blockpuzzle.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentServiceRest{

    @Autowired
    private CommentService commentService;

    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game) {
        return commentService.getComments(game);
    }

    @PostMapping
    public void addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }




}
