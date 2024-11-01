package org.mars;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataModel {
    private String firstUTC;
    private String pre;
    private String at;
    private String hws;
    private int monthOrdinal;
    private String northernSeason;
    private String southernSeason;
    private String lastUTC;
    private String season;
    private String wd;
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
        this.pre = pre;
        this.at = at;
        this.hws = hws;
        this.wd = wd;

        this.attributeMap = new HashMap<>();
        this.attributeMap.put("First_UTC", this.firstUTC);
        this.attributeMap.put("Last_UTC", this.lastUTC);
        this.attributeMap.put("Month_ordinal", String.valueOf(this.monthOrdinal));
        this.attributeMap.put("Northern_season", this.northernSeason);
        this.attributeMap.put("Southern_season", this.southernSeason);
        this.attributeMap.put("Season", this.season);
        this.attributeMap.put("pre", this.pre);
        this.attributeMap.put("at", this.at);
        this.attributeMap.put("hws", this.hws);
        this.attributeMap.put("wd", this.wd);
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
        return pre;
    }

    public String getAT() {
        return at;
    }

    public String getHWS() {
        return hws;
    }

    public String getWD() {
        return wd;
    }

}
