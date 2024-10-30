package org.mars;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Dotenv dotenv = Dotenv.load();
        GetAPI getAPI = new GetAPI();
        MongoDBClient mongoDBClient = new MongoDBClient();

        String response = getAPI.run("https://api.nasa.gov/insight_weather/?api_key=" + dotenv.get("NASA_API_KEY") + "&feedtype=json&ver=1.0");
        JSONArray responseArray = ParseJSON.parseResponseToJson(response);

        System.out.println(responseArray);

        mongoDBClient.insertToMongoCollection(responseArray);

    }
}