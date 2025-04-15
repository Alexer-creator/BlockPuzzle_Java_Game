package sk.tuke.kpi.kp.kp5.blockpuzzle.service;

import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Rate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements RatingService {
    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "sr161617";

    public static final String DELETE_STATEMENT = "DELETE FROM rating";
    public static final String INSERT_STATEMENT = "INSERT INTO rating VALUES (?, ?, ?)";
    public static final String SELECT_TOP_STATEMENT = "SELECT rating.playerName, rating.gameName, rating.rate FROM rating WHERE gameName = ? ORDER BY rate DESC LIMIT 4";
    public static final String SELECT_BOTTOM_STATEMENT = "SELECT rating.playerName, rating.gameName, rating.rate FROM rating WHERE gameName = ? ORDER BY rate ASC LIMIT 4";

    @Override
    public void addRate(Rate rate) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT)
        ) {
            statement.setString(1,  rate.getPlayerName());
            statement.setString(2,  rate.getGameName());
            statement.setInt(3,rate.getRate());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Rate> getTopRates(String gameName) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_TOP_STATEMENT)

        ) {
            statement.setString(1, gameName);
            try(ResultSet rs = statement.executeQuery()) {
                List<Rate> rates = new ArrayList<>();
                while (rs.next()) {
                    rates.add(new Rate(rs.getString(1), rs.getString(2), rs.getInt(3)));
                }
                return rates;
            }

        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Rate> getWorstRates(String gameName) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_BOTTOM_STATEMENT)
        ) {
            statement.setString(1, gameName);
            try(ResultSet rs = statement.executeQuery()) {
                List<Rate> rates = new ArrayList<>();
                while (rs.next()) {
                    rates.add(new Rate(rs.getString(1), rs.getString(2), rs.getInt(3)));
                }
                return rates;
            }

        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void resetRates() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}
