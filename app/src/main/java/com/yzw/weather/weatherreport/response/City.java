package com.yzw.weather.weatherreport.response;

/**
 * Created by yangzhiwei on 2016/12/7.
 */
public class City {
    public String aqi;
    public String pm10;
    public String pm25;
    public String qlty;

    @Override
    public String toString() {
        return "City{" +
                "aqi='" + aqi + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", qlty='" + qlty + '\'' +
                '}';
    }
}
