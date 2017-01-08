package com.yzw.weather.weatherreport.response;

/**
 * Created by yangzhiwei on 2016/12/6.
 * For Now & HourlyForecast
 */
public class Cond {
    public String code;
    public String txt;

    @Override
    public String toString() {
        return "Cond{" +
                "code='" + code + '\'' +
                ", txt='" + txt + '\'' +
                '}';
    }
}
