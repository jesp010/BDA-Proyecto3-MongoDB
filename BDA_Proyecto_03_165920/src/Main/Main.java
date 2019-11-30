package Main;

import BusinessObjects.Post;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class Main {

    public static void main(String[] args) {
        MongoClientSettings settings = Main.getMongoClientSettings();
        MongoClient client = MongoClients.create(settings);
        MongoDatabase database = client.getDatabase("appointments");
        MongoCollection<Post> clients = database.getCollection("clients", Post.class);
        
        ArrayList<Post> persons_temp = new ArrayList<>();
        for (Post p : clients.find()) {
            persons_temp.add(p);
        }

        //Getting person per ObjectId
        Post person = clients.find(eq("_id", persons_temp.get(0).getId())).first();
        System.out.println(person.toString());

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
