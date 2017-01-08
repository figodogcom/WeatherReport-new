package com.yzw.weather.weatherreport.response;

/**
 * Created by yangzhiwei on 2016/12/6.
 */
public class Update {
    public String loc;
    public String utc;

    @Override
    public String toString() {
        return "Update{" +
                "loc='" + loc + '\'' +
                ", utc='" + utc + '\'' +
                '}';
    }
}
