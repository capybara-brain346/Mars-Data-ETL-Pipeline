package org.mars;

public class DataModel {
    private String firstUTC;
    private String atmosphericPressure;
    private String atmosphericTemperature;
    private String horizontalWindSpeed;
    private int monthOrdinal;
    private String northernSeason;
    private String southernSeason;
    private String lastUTC;
    private String season;
    private String windDirection;

    public DataModel(String firstUTC, String lastUTC, int monthOrdinal, String northernSeason, String southernSeason, String season, String atmosphericPressure, String atmosphericTemperature, String horizontalWindSpeed, String windDirection) {
        this.firstUTC = firstUTC;
        this.lastUTC = lastUTC;
        this.monthOrdinal = monthOrdinal;
        this.northernSeason = northernSeason;
        this.southernSeason = southernSeason;
        this.season = season;
        this.atmosphericPressure = atmosphericPressure;
        this.atmosphericTemperature = atmosphericTemperature;
        this.horizontalWindSpeed = horizontalWindSpeed;
        this.windDirection = windDirection;
    }

    public String getFirstUTC() {
        return firstUTC;
    }

    public String getLastUTC() {
        return lastUTC;
    }

    public int getMonthOrdinal() {
        return monthOrdinal;
    }

    public String getNorthernSeason() {
        return northernSeason;
    }

    public String getSouthernSeason() {
        return southernSeason;
    }

    public String getSeason() {
        return season;
    }

    public String getPRE() {
        return atmosphericPressure;
    }

    public String getAT() {
        return atmosphericTemperature;
    }

    public String getHWS() {
        return horizontalWindSpeed;
    }

    public String getWD() {
        return windDirection;
    }

}
