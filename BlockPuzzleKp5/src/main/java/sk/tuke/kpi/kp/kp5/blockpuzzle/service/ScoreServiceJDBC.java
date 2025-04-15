package sk.tuke.kpi.kp.kp5.blockpuzzle.service;

import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService{

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "sr161617";
    public static final String DELETE_STATEMENT = "DELETE FROM score";
    public static final String INSERT_STATEMENT = "INSERT INTO score(playerName, gameName, points, playedDate) VALUES(?,?,?,?)";
    public static final String SELECT_STATEMENT = "SELECT score.playername, score.gameName, score.points, score.playedDate FROM score where gameName = ? ORDER BY points DESC LIMIT 10";

    @Override
    public void addScore(Score score) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT)
        ) {
            statement.setString(1,  score.getPlayerName());
            statement.setString(2,  score.getGameName());
            statement.setInt(3,score.getPoints());
            statement.setTimestamp(4, new Timestamp(score.getPlayedDate().getTime()));      //

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_STATEMENT)

        ) {
            statement.setString(1, game);
            try(ResultSet rs = statement.executeQuery()) {
                List<Score> scores = new ArrayList<>();
                while (rs.next()) {
                    scores.add(new Score(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                }
                return scores;
            }

        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void resetHof() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}
