package sk.tuke.kpi.kp.kp5.blockpuzzle.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Score.getTopScores",
                query = "SELECT s FROM Score s WHERE s.gameName = :game ORDER BY s.points DESC"
        ),
        @NamedQuery(
                name = "Score.resetScores",
                query = "DELETE FROM Score"
        )
})
public class Score implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String playerName;

    private String gameName;

    private int points;

    private Date playedDate;

    public Score() {}

    public Score(String playerName, String gameName, int points, Date playedDate) {
        this.playerName = playerName;
        this.gameName = gameName;
        this.points = points;
        this.playedDate = playedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedDate() {
        return playedDate;
    }

    public void setPlayedDate(Date playedDate) {
        this.playedDate = playedDate;
    }

    @Override
    public String toString() {
        return "Score{" +
                "playerName='" + playerName + '\'' +
                ", gameName='" + gameName + '\'' +
                ", points=" + points +
                ", playedDate=" + playedDate +
                '}';
    }
}
