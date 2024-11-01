package org.mars;

import com.mongodb.client.MongoCollection;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import org.json.JSONArray;

import java.io.IOException;
import java.util.Optional;

public class Extract {
    private static final Dotenv dotenv = Dotenv.load();

    private static void sendDataToMongoDB(GetAPI getAPI, MongoUtils mongoDBClient) throws IOException {
        Optional<MongoCollection<Document>> collection = mongoDBClient.connectToMongoCollection();
        assert collection.isPresent();

        String response = getAPI.run("https://api.nasa.gov/insight_weather/?api_key=" + dotenv.get("NASA_API_KEY") + "&feedtype=json&ver=1.0");
        JSONArray responseArray = ParseJSON.parseResponseToJson(response);

        mongoDBClient.insertToMongoCollection(collection.get(), responseArray);
    }

    public static void main(String[] args) throws IOException {
        GetAPI getAPI = new GetAPI();
        MongoUtils mongoDBClient = new MongoUtils();
        sendDataToMongoDB(getAPI, mongoDBClient);
    }
}
