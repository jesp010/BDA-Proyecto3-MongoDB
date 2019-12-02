package BusinessObjects;

import java.util.Date;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 * @author Juan Enrique Solis Perla
 * @ID: 165920 Advanced Databases Class, ISW, ITSON
 */
public class Comment implements Comparable<Comment> {

    private ObjectId id;
    private Date date;
    private String comment;
    private User user;

    public Comment() {
    }

    public Comment(ObjectId id, Date date, String comment, User user) {
        this.id = id;
        this.date = date;
        this.comment = comment;
        this.user = user;
    }

    public Comment(Date date, String comment, User user) {
        this.date = date;
        this.comment = comment;
        this.user = user;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Comment other = (Comment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Comment comment) {
        return this.date.compareTo(comment.getDate());
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", date=" + date + ", comment=" + comment + ", user=" + user + '}';
    }
}
