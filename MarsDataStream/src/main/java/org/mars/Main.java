package org.mars;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private static final Dotenv dotenv = Dotenv.load();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final DataRepository dataRepository = new DataRepository();

    private static void sendDataToMongoDB(GetAPI getAPI, MongoDBClient mongoDBClient) throws IOException {
        String response = getAPI.run("https://api.nasa.gov/insight_weather/?api_key=" + dotenv.get("NASA_API_KEY") + "&feedtype=json&ver=1.0");
        JSONArray responseArray = ParseJSON.parseResponseToJson(response);

        System.out.println(responseArray);

        mongoDBClient.insertToMongoCollection(responseArray);
    }

    private static void retrieveDataFromMongoDB(MongoDBClient mongoDBClient) throws JsonProcessingException {
        FindIterable<Document> documents = mongoDBClient.retrieveDocuments();
        for (Document doc : documents) {
            JSONObject value = new JSONObject(doc.toJson());
            value.remove("_id");

            String firstUTC = DateUtil.convertUTCtoMySQLFormat(value.getString("First_UTC"));
            String lastUTC = DateUtil.convertUTCtoMySQLFormat(value.getString("Last_UTC"));
            int monthOrdinal = value.getInt("Month_ordinal");
            String northernSeason = value.getString("Northern_season");
            String southernSeason = value.getString("Southern_season");
            String season = value.getString("Season");
            String preJson = value.getJSONObject("PRE").toString();
            String atJson = value.getJSONObject("AT").toString();
            String hwsJson = value.getJSONObject("HWS").toString();
            String wdJson = value.getJSONObject("WD").toString();

            DataModel dataModel = new DataModel(firstUTC, lastUTC, monthOrdinal, northernSeason, southernSeason, season, preJson, atJson, hwsJson, wdJson);

            try {
                dataRepository.insertMarsWeatherData(dataModel);
                System.out.println("Mars weather data inserted successfully.");
            } catch (SQLException e) {
                System.err.println("Failed to insert Mars weather data: " + e.getMessage());
            }
        }
    }


    public static void main(String[] args) throws IOException {
        GetAPI getAPI = new GetAPI();
        MongoDBClient mongoDBClient = new MongoDBClient();
//        sendDataToMongoDB(getAPI, mongoDBClient);
        retrieveDataFromMongoDB(mongoDBClient);
    }
}