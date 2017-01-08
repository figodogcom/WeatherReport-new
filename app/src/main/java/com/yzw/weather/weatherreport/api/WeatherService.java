package com.yzw.weather.weatherreport.api;

import com.yzw.weather.weatherreport.response.ReaponseWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yangzhiwei on 2016/12/6.
 */
public interface WeatherService {
    @GET("weather")
    Call<ReaponseWeather> weather(@Query("key") String key,
                                  @Query("city") String city);

}
