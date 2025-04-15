package sk.tuke.kpi.kp.kp5.blockpuzzle.service;

import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Comment;
import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Rate;

import java.util.Date;

public class TestManagerJDBC {
    public static void main(String[] args) {
        CommentServiceJDBC commentService = new CommentServiceJDBC();

        commentService.reset();
        commentService.addComment(new Comment("Michael Jordan","BlockPuzzle", "Not a bad game", new Date()));
        commentService.addComment(new Comment("Stephen Curry","BlockPuzzle", "That was hard one", new Date()));
        commentService.addComment(new Comment("Kobe Bryant","BlockPuzzle", "I've barely solved this one", new Date()));

        RatingServiceJDBC ratingService = new RatingServiceJDBC();
        ratingService.resetRates();

        ratingService.addRate(new Rate("Michael Jordan","BlockPuzzle",7));
        ratingService.addRate(new Rate("Stephen Curry","BlockPuzzle",8));
        ratingService.addRate(new Rate("David Jordan","BlockPuzzle",4));
        ratingService.addRate(new Rate("Tracy McGrady","BlockPuzzle",6));
        ratingService.addRate(new Rate("Kobe Bryant","BlockPuzzle",9));
        ratingService.addRate(new Rate("Trae Yang","BlockPuzzle",2));
        ratingService.addRate(new Rate("Lebron James","BlockPuzzle",1));
        ratingService.addRate(new Rate("Kevin Garnet","BlockPuzzle",4));
        ratingService.addRate(new Rate("Tim Duncan","BlockPuzzle",5));
        ratingService.addRate(new Rate("Russel Westbrook","BlockPuzzle",10));
        ratingService.addRate(new Rate("Jason Tatum ","BlockPuzzle",8));
        ratingService.addRate(new Rate("James Harden","BlockPuzzle",3));

    }
}
