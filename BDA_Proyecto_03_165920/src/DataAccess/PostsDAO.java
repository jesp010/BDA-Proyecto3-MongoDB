package DataAccess;

import BusinessObjects.Post;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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

}
