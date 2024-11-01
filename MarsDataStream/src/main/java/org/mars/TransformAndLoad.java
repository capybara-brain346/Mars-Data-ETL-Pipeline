package org.mars;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TransformAndLoad {
    private static final DataRepository dataRepository = new DataRepository();


    private static void retrieveDataFromMongoDB(MongoUtils mongoDBClient) {
        Optional<MongoCollection<Document>> collection = mongoDBClient.connectToMongoCollection();
        assert collection.isPresent();

        FindIterable<Document> documents = mongoDBClient.retrieveDocuments(collection.get());
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
            List<String> attributes = new ArrayList<>(Arrays.asList("First_UTC","Last_UTC"));
            try {
                dataRepository.insertMarsWeatherData(dataModel, attributes);
                System.out.println("Mars weather data inserted successfully.");
            } catch (SQLException e) {
                System.err.println("Failed to insert Mars weather data: " + e.getMessage());
            }
        }
    }


    public static void main(String[] args) {
        MongoUtils mongoDBClient = new MongoUtils();
        retrieveDataFromMongoDB(mongoDBClient);
    }
}