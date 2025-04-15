package sk.tuke.kpi.kp.kp5.blockpuzzle.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rate")
@NamedQueries({ // What should I write here??
        @NamedQuery(
                name = "Rate.getTopRates",
                query = "SELECT r FROM Rate r WHERE r.gameName = :game ORDER BY r.rate DESC"
        ),
        @NamedQuery(
                name = "Rate.getWorstRates",
                query = "SELECT r FROM Rate r WHERE r.gameName = :game ORDER BY r.rate ASC"
        ),
        @NamedQuery(
                name = "Rate.resetRates",
                query = "DELETE FROM Rate"
        )
})

public class Rate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String playerName;

    private String gameName;

    private int rate;

    public Rate() { }

    public Rate(String playerName, String gameName, int rate) {
        this.playerName = playerName;
        this.gameName = gameName;
        this.rate = rate;
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "author='" + playerName + '\'' +
                ", gameName='" + gameName + '\'' +
                ", rate=" + rate +
                '}';
    }
}
