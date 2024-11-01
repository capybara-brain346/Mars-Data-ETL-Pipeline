package org.mars;

import com.mongodb.*;
import com.mongodb.client.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class MongoUtils {
    private static final Logger logger = Logger.getLogger(MongoUtils.class.getName());

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

    public void insertToMongoCollection(MongoCollection<Document> collection, JSONArray responseArray) {
        logger.info("Converting response to documents.");

        List<Document> documents = new ArrayList<>();

        for (int i = 0; i < responseArray.length(); i++) {
            JSONObject responseObject = responseArray.getJSONObject(i);
            Document document = Document.parse(responseObject.toString());
            documents.add(document);
        }

        try {
            logger.info("Inserting documents.");
            collection.insertMany(documents);
            this.mongoClient.close();

        } catch (MongoException e) {
            logger.severe("An exception has occurred: " + e);
            this.mongoClient.close();
        }
    }

    public FindIterable<Document> retrieveDocuments(MongoCollection<Document> collection) {
        logger.info("Retrieving all documents.");
        FindIterable<Document> iterDocuments = collection.find();
        for (Document d : iterDocuments) {
            System.out.println(d.toJson());
        }
        return iterDocuments;
    }
}
