package com.yzw.weather.weatherreport.response;

/**
 * Created by yangzhiwei on 2016/12/6.
 */
public class Basic {
    public String city;
    public String cnty;
    public String id;
    public String lat;
    public String lon;
    public Update update;

    @Override
    public String toString() {
        return "Basic{" +
                "city='" + city + '\'' +
                ", cnty='" + cnty + '\'' +
                ", id='" + id + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", update=" + update +
                '}';
    }
}
