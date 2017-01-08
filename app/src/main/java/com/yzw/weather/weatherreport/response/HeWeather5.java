package com.yzw.weather.weatherreport.response;

import com.yzw.weather.weatherreport.response.suggestion.Suggestion;

import java.util.List;

/**
 * Created by yangzhiwei on 2016/12/6.
 */
public class HeWeather5 {
    public Aqi aqi;
    public Basic basic;
    public Now now;
    public String status;
    public List<DailyForecast> daily_forecast;
    public List<HourlyForecast> hourly_forecast;
    public Suggestion suggestion;

    @Override
    public String toString() {
        return "HeWeather5{" +
                "aqi=" + aqi +
                ", basic=" + basic +
                ", now=" + now +
                ", status='" + status + '\'' +
                ", daily_forecast=" + daily_forecast +
                ", hourly_forecast=" + hourly_forecast +
                ", suggestion=" + suggestion +
                '}';
    }
}
