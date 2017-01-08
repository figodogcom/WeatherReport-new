package com.yzw.weather.weatherreport.model;

/**
 * Created by yangzhiwei on 2016/9/21.
 */
public final class City/* implements BaseColumns*/ {

    public static final String COLUMN_NAME = "name";

    public static final int COLUMN_COUNT = 5;

    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_NAME = 1;



    public String id;
    public String cityZh;
    public String cityEn;
    public String countryCode;
    public String countryEn;
    public String countryZh;
    public String provinceEn;
    public String leaderEn;
    public String leaderZh;
    public String continent;
    public String lat;
    public String lon;

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", cityZh='" + cityZh + '\'' +
                ", cityEn='" + cityEn + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryEn='" + countryEn + '\'' +
                ", countryZh='" + countryZh + '\'' +
                ", provinceEn='" + provinceEn + '\'' +
                ", leaderEn='" + leaderEn + '\'' +
                ", leaderZh='" + leaderZh + '\'' +
                ", continent='" + continent + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }
}
