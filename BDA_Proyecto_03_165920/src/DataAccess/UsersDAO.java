package DataAccess;

import BusinessObjects.User;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class UsersDAO {
    private MongoClientSettings settings;
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<User> postCollection;

    public UsersDAO() {
        settings = BaseDAO.getMongoClientSettings();
        client = MongoClients.create(settings);
        database = client.getDatabase(BaseDAO.DB_NAME);
        postCollection = database.getCollection("users", User.class);
    }
    
}
