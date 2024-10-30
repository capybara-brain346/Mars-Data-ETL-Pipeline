package org.mars;

import org.json.JSONArray;
import org.json.JSONObject;

public class ParseJSON {
    public static JSONArray parseResponseToJson(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONArray responseArray = new JSONArray();
        JSONArray solArray = responseJson.getJSONArray("sol_keys");
//        System.out.println(solArray);

        for (int i = 0; i < solArray.length(); i++) {
            JSONObject sols = responseJson.getJSONObject(solArray.getString(i));
            responseArray.put(sols);
        }
        return responseArray;
    }
}
