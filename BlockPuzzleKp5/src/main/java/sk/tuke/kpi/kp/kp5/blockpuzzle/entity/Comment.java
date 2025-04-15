package sk.tuke.kpi.kp.kp5.blockpuzzle.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@NamedQueries({
    @NamedQuery(
            name = "Comment.getComments",
            query = "SELECT c FROM Comment c WHERE c.gameName =:game"
    ),
    @NamedQuery(
            name = "Comment.reset",
            query = "DELETE FROM Comment"
    )
})
public class Comment implements Serializable {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String playerName;

    private String gameName;

    private String commentBody;

    private Date date;

    public Comment() {}

    public Comment(String playerName, String gameName, String commentBody, Date date) {
        this.playerName = playerName;
        this.gameName = gameName;
        this.commentBody = commentBody;
        this.date = date;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
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

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "author='" + playerName + '\'' +
                ", gameName='" + gameName + '\'' +
                ", commentBody='" + commentBody + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
