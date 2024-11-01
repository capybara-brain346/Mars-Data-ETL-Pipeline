package org.mars;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataModel {
    private String firstUTC;
    private String PRE;
    private String AT;
    private String HWS;
    private int monthOrdinal;
    private String northernSeason;
    private String southernSeason;
    private String lastUTC;
    private String season;
    private String WD;
    public Map<String, String> attributeMap;

    public DataModel() {

    }

    public DataModel(String firstUTC, String lastUTC, int monthOrdinal, String northernSeason, String southernSeason, String season, String PRE, String AT, String HWS, String WD) {
        this.firstUTC = firstUTC;
        this.lastUTC = lastUTC;
        this.monthOrdinal = monthOrdinal;
        this.northernSeason = northernSeason;
        this.southernSeason = southernSeason;
        this.season = season;
        this.PRE = PRE;
        this.AT = AT;
        this.HWS = HWS;
        this.WD = WD;

        this.attributeMap = new HashMap<>();
        this.attributeMap.put("First_UTC", this.firstUTC);
        this.attributeMap.put("Last_UTC", this.lastUTC);
        this.attributeMap.put("Month_ordinal", String.valueOf(this.monthOrdinal));
        this.attributeMap.put("Northern_season", this.northernSeason);
        this.attributeMap.put("Southern_season", this.southernSeason);
        this.attributeMap.put("Season", this.season);
        this.attributeMap.put("PRE", this.PRE);
        this.attributeMap.put("AT", this.AT);
        this.attributeMap.put("HWS", this.HWS);
        this.attributeMap.put("WD", this.WD);
    }

    public Map<String, String> getSubset(List<String> attributes) {
        return this.attributeMap;
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
        return PRE;
    }

    public String getAT() {
        return AT;
    }

    public String getHWS() {
        return HWS;
    }

    public String getWD() {
        return WD;
    }

}
