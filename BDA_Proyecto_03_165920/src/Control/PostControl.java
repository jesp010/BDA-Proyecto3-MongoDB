package Control;

import BusinessObjects.Comment;
import BusinessObjects.Post;
import DataAccess.PostsDAO;
import java.util.ArrayList;
import org.bson.types.ObjectId;

/**
 * @author Juan Enrique Solis Perla
 * @ID: 165920 Advanced Databases Class, ISW, ITSON
 */
public class PostControl {

    private PostsDAO postsDAO;

    public PostControl() {
        this.postsDAO = new PostsDAO();
    }

    public ArrayList<Post> findAll() {
        return this.postsDAO.findAll();
    }

    public Post findByID(ObjectId id) {
        return this.postsDAO.findByID(id);
    }

    public void save(Post post) {
        this.postsDAO.save(post);
    }

    public void update(Post post) {
        this.postsDAO.update(post);
    }

    public void addComment(Post post, Comment comment) {
        this.postsDAO.addComment(post, comment);
    }

    public void remove(Post post) {
        this.postsDAO.remove(post);
    }
}
