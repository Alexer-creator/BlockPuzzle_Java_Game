package sk.tuke.kpi.kp.kp5.blockpuzzle.service;

import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "sr161617";

    public static final String RESET_STATEMENT = "DELETE FROM comments";
    public static final String SELECT_ALL_COMMENTS_STATEMENT = "SELECT playerName, gameName, commentBody, comment_date FROM comments WHERE gameName = ?";
    public static final String INSERT_STATEMENT = "INSERT INTO comments(playerName, gameName, commentBody, comment_date) VALUES (?,?,?,?)";

    @Override
    public void addComment(Comment comment) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT)
        ) {
            statement.setString(1,  comment.getPlayerName());
            statement.setString(2,  comment.getGameName());
            statement.setString(3,comment.getCommentBody());
            statement.setTimestamp(4, new Timestamp(comment.getDate().getTime()));      //

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Comment> getComments(String gameName) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_COMMENTS_STATEMENT)
        ) {
            statement.setString(1, gameName);
            try(ResultSet rs = statement.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    comments.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4)));
                }
                return comments;
            }

        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(RESET_STATEMENT);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}
