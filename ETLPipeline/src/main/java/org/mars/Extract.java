package org.mars;

import com.mongodb.*;
import com.mongodb.client.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

import java.util.logging.Logger;

public class Extract {
    private static final Logger logger = Logger.getLogger(Extract.class.getName());

    private final Dotenv dotenv = Dotenv.load();
    private final String dbUsername = dotenv.get("DB_USERNAME");
    private final String dbPassword = dotenv.get("DB_PASSWORD");
    private final String dbURI = dotenv.get("DB_URI");
    private final String connectionString = "mongodb+srv://" + dbUsername + ":" + dbPassword + dbURI;

    private final ServerApi serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build();
    private final MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();
    public MongoClient mongoClient = MongoClients.create(this.settings);

    private MongoCollection<Document> connectToMongoCollection() {
        try {
            MongoDatabase database = this.mongoClient.getDatabase("MarsDB");
            logger.info("Connected to WeatherData collection.");
            MongoCollection<Document> collection = database.getCollection("WeatherData");
            logger.info("Connected to WeatherData collection.");
            return collection;
        } catch (MongoException e) {
            logger.severe("An exception has occurred: " + e);
        }

        return null;
    }

    public void retrieveDocuments() {
        logger.info("Retrieving all documents.");
        MongoCollection<Document> collection = this.connectToMongoCollection();
        assert collection != null;
        FindIterable<Document> iterDocuments = collection.find();
        for (Document d : iterDocuments) {
            System.out.println(d.toJson());
        }
    }
}
