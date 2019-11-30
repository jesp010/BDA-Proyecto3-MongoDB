package BusinessObjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import org.bson.types.ObjectId;

public class Post {

    private ObjectId id;
    private Date date;
    private User user;
    private String message;
    private ArrayList<Tag> tags;
    private ArrayList<Comment> comments;

    public Post() {
    }

    public Post(ObjectId id, Date date, User user, String message, ArrayList<Tag> tags, ArrayList<Comment> comments) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.message = message;
        this.tags = tags;
        this.comments = comments;
    }

    public Post(Date date, User user, String message, ArrayList<Tag> tags, ArrayList<Comment> comments) {
        this.date = date;
        this.user = user;
        this.message = message;
        this.tags = tags;
        this.comments = comments;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Post other = (Post) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", date=" + date + ", user=" + user + ", message=" + message + ", tags=" + tags + ", comments=" + comments + '}';
    }
}
