package DataAccess;

import com.mongodb.MongoClientSettings;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * @author Juan Enrique Solis Perla
 * @ID: 165920 Advanced Databases Class, ISW, ITSON
 */
public class BaseDAO {
    public static final String DB_NAME = "faceblog";
    
        public static MongoClientSettings getMongoClientSettings() {
        CodecRegistry pojoCodecRegistry
                = CodecRegistries.fromRegistries(com.mongodb.MongoClient.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry).build();

        return settings;
    }
}
