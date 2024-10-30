package org.mars;

public class DataModel {
    private String firstUTC;
    private String lastUTC;
    private int monthOrdinal;
    private String northernSeason;
    private String southernSeason;
    private String season;
    private String PRE;
    private String AT;
    private String HWS;
    private String WD;
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstUTC(String firstUTC) {
        this.firstUTC = firstUTC;
    }

    public void setLastUTC(String lastUTC) {
        this.lastUTC = lastUTC;
    }

    public void setMonthOrdinal(int monthOrdinal) {
        this.monthOrdinal = monthOrdinal;
    }

    public void setNorthernSeason(String northernSeason) {
        this.northernSeason = northernSeason;
    }

    public void setSouthernSeason(String southernSeason) {
        this.southernSeason = southernSeason;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setPRE(String PRE) {
        this.PRE = PRE;
    }

    public void setHWS(String HWS) {
        this.HWS = HWS;
    }

    public void setAT(String AT) {
        this.AT = AT;
    }

    public void setWD(String WD) {
        this.WD = WD;
    }

    public String getFirstUTC() {
        return firstUTC;
    }

    public String getId() {
        return id;
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
