package DataAccess;

import BusinessObjects.User;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * @author Juan Enrique Solis Perla
 * @ID: 165920 Advanced Databases Class, ISW, ITSON
 */
public class UsersDAO {

    private MongoClientSettings settings;
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<User> userCollection;

    public UsersDAO() {
        settings = BaseDAO.getMongoClientSettings();
        client = MongoClients.create(settings);
        database = client.getDatabase(BaseDAO.DB_NAME);
        userCollection = database.getCollection("users", User.class);
    }

    public ArrayList<User> findAll() {
        ArrayList<User> users_temp = new ArrayList<>();
        for (User u : userCollection.find()) {
            users_temp.add(u);
        }
        return users_temp;
    }

    public User findByID(ObjectId id) {
        //Getting person per ObjectId
        User user = userCollection.find(eq("_id", id)).first();
        System.out.println(user != null ? user.toString() : "null findByID");
        return user;
    }

    public User findByEmail(String email) {
        return userCollection.find(eq("email", email)).first();
    }

    public boolean save(User user) {
        User u = user;
        u.setId(new ObjectId());
        try {
            userCollection.insertOne(u);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(User user) {
        try {
            userCollection.updateOne(eq("_id", user.getId()), new Document("$set",
                    new Document("birthDate", user.getBirthDate())
                            .append("email", user.getEmail())
                            .append("favMovies", user.getFavMovies())
                            .append("favMusicGenre", user.getFavMusicGenre())
                            .append("password", user.getPassword())
                            .append("sex", user.getSex().toString())
                            .append("username", user.getUsername())
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void remove(User user) {
        userCollection.deleteOne(eq("_id", user.getId()));
    }
}
