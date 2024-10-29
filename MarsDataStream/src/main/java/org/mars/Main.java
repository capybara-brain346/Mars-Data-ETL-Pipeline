package org.mars;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GetAPI getAPI = new GetAPI();
        MongoDBClient mongoDBClient = new MongoDBClient();

        String response = getAPI.run("https://api.nasa.gov/insight_weather/?api_key=dphfgZwR6wN9w9i4ktUiswSVc85bPHXD0Ah5GXkd&feedtype=json&ver=1.0");
        JSONObject responseJson = new JSONObject(response);
        responseJson.remove("sol_keys");
        JSONArray responseArray = new JSONArray();
        responseArray.put(responseJson);

        mongoDBClient.insertToMongoCollection(responseArray);

//        System.out.println(responseArray.getJSONObject(0));
    }
}