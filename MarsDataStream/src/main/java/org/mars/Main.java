package org.mars;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GetAPI getAPI = new GetAPI();
        String run = getAPI.run("https://api.nasa.gov/insight_weather/?api_key=dphfgZwR6wN9w9i4ktUiswSVc85bPHXD0Ah5GXkd&feedtype=json&ver=1.0");
        System.out.println(run);
    }
}