package org.mars;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;

import java.io.IOException;

public class Main {
    private static final Dotenv dotenv = Dotenv.load();

    private static void sendDataToMongoDB(GetAPI getAPI, MongoDBClient mongoDBClient) throws IOException {
        String response = getAPI.run("https://api.nasa.gov/insight_weather/?api_key=" + dotenv.get("NASA_API_KEY") + "&feedtype=json&ver=1.0");
        JSONArray responseArray = ParseJSON.parseResponseToJson(response);

        System.out.println(responseArray);

        mongoDBClient.insertToMongoCollection(responseArray);
    }

    private static void retrieveDataFromMongoDB(GetAPI getAPI, MongoDBClient mongoDBClient) {
        mongoDBClient.retrieveDocuments();
    }


    public static void main(String[] args) throws IOException {
        GetAPI getAPI = new GetAPI();
        MongoDBClient mongoDBClient = new MongoDBClient();
        sendDataToMongoDB(getAPI, mongoDBClient);
        retrieveDataFromMongoDB(getAPI, mongoDBClient);


    }
}