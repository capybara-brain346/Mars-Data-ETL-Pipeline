package org.mars;

import com.mongodb.*;
import com.mongodb.client.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

import java.util.Optional;
import java.util.logging.Logger;

public class MongoUtils {
    private static final Logger logger = Logger.getLogger(MongoUtils.class.getName());

    private final Dotenv dotenv = Dotenv.load();
    private final String dbUsername = dotenv.get("DB_USERNAME");
    private final String dbPassword = dotenv.get("DB_PASSWORD");
    private final String dbURI = dotenv.get("DB_URI");
    private final String connectionString = "mongodb+srv://" + dbUsername + ":" + dbPassword + dbURI;

    private final ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
    private final MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();
    private final MongoClient mongoClient = MongoClients.create(this.settings);

    public Optional<MongoCollection<Document>> connectToMongoCollection() {
        try {
            MongoDatabase database = this.mongoClient.getDatabase("MarsDB");
            logger.info("Connected to WeatherData collection.");
            MongoCollection<Document> collection = database.getCollection("WeatherData");
            logger.info("Connected to WeatherData collection.");
            return Optional.of(collection);
        } catch (MongoException e) {
            logger.severe("An exception has occurred: " + e);
        }
        return Optional.empty();
    }


    public FindIterable<Document> retrieveDocuments(MongoCollection<Document> collection) {
        logger.info("Retrieving all documents.");
        return collection.find();
    }
}
