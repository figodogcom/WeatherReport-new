package com.yzw.weather.weatherreport.response;

import java.util.List;

/**
 * Created by yangzhiwei on 2016/12/6.
 */
public class ReaponseWeather {
//    @SerializedName("HeWeather5")
    public List<HeWeather5> HeWeather5;



    @Override
    public String toString() {
        return "ReaponseForecast{" +
                "HeWeather5=" + HeWeather5 +
                '}';
    }
}
