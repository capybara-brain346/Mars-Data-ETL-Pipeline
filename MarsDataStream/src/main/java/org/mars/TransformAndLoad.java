package org.mars;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

public class TransformAndLoad {
    private static final DataRepository dataRepository = new DataRepository();


    private static void retrieveDataFromMongoDB(MongoUtils mongoDBClient) {
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
        MongoUtils mongoDBClient = new MongoUtils();
        retrieveDataFromMongoDB(mongoDBClient);
    }
}