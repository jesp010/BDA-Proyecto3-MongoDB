package DataAccess;

import BusinessObjects.Comment;
import BusinessObjects.Post;
import BusinessObjects.Tag;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.Collections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * @author Juan Enrique Solis Perla
 * @ID: 165920 Advanced Databases Class, ISW, ITSON
 */
public class PostsDAO {

    private MongoClientSettings settings;
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Post> postCollection;

    public PostsDAO() {
        settings = BaseDAO.getMongoClientSettings();
        client = MongoClients.create(settings);
        database = client.getDatabase(BaseDAO.DB_NAME);
        postCollection = database.getCollection("posts", Post.class);
    }

    public ArrayList<Post> findAll() {
        ArrayList<Post> posts_temp = new ArrayList<>();
        for (Post p : postCollection.find()) {
            posts_temp.add(p);
        }
        Collections.sort(posts_temp);
        return posts_temp;
    }

    public Post findByID(ObjectId id) {
        //Getting person per ObjectId
        Post post = postCollection.find(eq("_id", id)).first();
        System.out.println(post != null ? post.toString() : "null findByID");
        return post;
    }

    public void save(Post post) {
        Post p = post;
        if (p.getId() == null) {
            p.setId(new ObjectId());
        }

        postCollection.insertOne(p);
    }

    public void update(Post post) {
        postCollection.updateOne(eq("_id", post.getId()), new Document("$set",
                new Document("comments", post.getComments())
                        .append("date", post.getDate())
                        .append("message", post.getMessage())
                        .append("tags", post.getTags())
                        .append("user", post.getUser())
        ));
    }

    public void addComment(Post post, Comment comment) {
        postCollection.updateOne(eq("_id", post.getId()), Updates.addToSet("comments", comment));
    }

    public void remove(Post post) {
        postCollection.deleteOne(eq("_id", post.getId()));
    }

    public void removeComment(Post p, String commentID) {
//        postCollection.updateOne(eq("_id", p.getId()), null
////   { _id: id },{ $pull: { 'contact.phone': { number: '+1786543589455' } } }
//);

        Bson query = new Document().append("_id", p.getId());
        Bson fields = new Document().append("comments", new Document().append("_id", new ObjectId(commentID)));
        Bson update = new Document("$pull", fields);
        postCollection.updateOne(query, update);
    }

    public ArrayList<Post> findByTag(String tag) {
        String pattern = ""+ tag +"";
        ArrayList<Post> posts = new ArrayList<>();

        Document regQuery = new Document();
        regQuery.append("$regex", pattern);
        regQuery.append("$options", "i");

        Document findQuery = new Document();
        findQuery.append("tags.tag", regQuery);
        for (Post p : postCollection.find(findQuery)) {
            posts.add(p);
        }
        Collections.sort(posts);
        
        for(Post p: posts){
            System.out.println(p.toString());
        }
        return posts;
    }
    
//    public ArrayList<Post> findByTag(String tag) {
//        ArrayList<Post> posts = new ArrayList<>();
//        ArrayList<Post> temp_posts = findAll();
//        for (Post p : temp_posts) {
//            if(findTagInArray(p.getTags(), tag)) posts.add(p);
//        }
//        return posts;
//    }
//
//    private boolean findTagInArray(ArrayList<Tag> tags, String tag){
//        for(Tag t: tags){
//            if(t.getTag().equalsIgnoreCase(tag)) {
//                System.out.println("MAtched " + t.getTag());
//                return true;
//            }
//        }
//        return false;
//    }
}
