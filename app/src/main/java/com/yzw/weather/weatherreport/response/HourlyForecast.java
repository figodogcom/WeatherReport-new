package com.yzw.weather.weatherreport.response;

/**
 * Created by yangzhiwei on 2016/12/7.
 */
public class HourlyForecast {
    public Cond cond;
    public String date;
    public String hum;
    public String pop;
    public String pres;
    public String tmp;
    public Wind wind;

    @Override
    public String toString() {
        return "Hourly_forecast{" +
                "cond=" + cond +
                ", date='" + date + '\'' +
                ", hum='" + hum + '\'' +
                ", pop='" + pop + '\'' +
                ", pres='" + pres + '\'' +
                ", tmp='" + tmp + '\'' +
                ", wind=" + wind +
                '}';
    }
}
