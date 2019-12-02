package Main;

import BusinessObjects.Comment;
import BusinessObjects.FavoriteMovie;
import BusinessObjects.FavoriteMusicGenre;
import BusinessObjects.Post;
import BusinessObjects.Tag;
import BusinessObjects.User;
import Control.PostControl;
import Control.UserControl;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.Date;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

public class Main {

    public static void main(String[] args) {
//        MongoClientSettings settings = Main.getMongoClientSettings();
//        MongoClient client = MongoClients.create(settings);
//        MongoDatabase database = client.getDatabase("faceblog");
//        MongoCollection<User> userCollection = database.getCollection("users", User.class);
//        
//        ArrayList<Post> persons_temp = new ArrayList<>();
//        for (Post p : clients.find()) {
//            persons_temp.add(p);
//        }
//
//        //Getting person per ObjectId
//        Post person = clients.find(eq("_id", persons_temp.get(0).getId())).first();
//        System.out.println(person.toString());
        
        UserControl uc = new UserControl();
//        System.out.println(uc.findByEmail("pepe@gmai.com").toString());
        
        User u = uc.findByEmail("root12@gmai.com");
        u.setUsername("pepeXDXD123");
        ArrayList<FavoriteMusicGenre> fmg = u.getFavMusicGenre();
        fmg.add(new FavoriteMusicGenre(new ObjectId(), "Cumbias", "Musica de baile"));
        
        ArrayList<FavoriteMovie> fm = u.getFavMovies();
        fm.add(new FavoriteMovie(new ObjectId(), "spiderman", "spidermanxD 2008 the best"));
        
        uc.update(u);
        
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag(new ObjectId(), "tag1"));
        tags.add(new Tag(new ObjectId(), "tag3"));
        tags.add(new Tag(new ObjectId(), "tag2"));
        tags.add(new Tag(new ObjectId(), "tag4"));
        
        PostControl pc = new PostControl();
        pc.save(new Post(new ObjectId(), new Date(), u, "qwwqeqweqeqeqwe dsnjasndjasnd nsajdnnd jasnd", tags, new ArrayList<>()));
    }

    public static MongoClientSettings getMongoClientSettings() {
        CodecRegistry pojoCodecRegistry
                = CodecRegistries.fromRegistries(com.mongodb.MongoClient.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry).build();

        return settings;
    }
}
