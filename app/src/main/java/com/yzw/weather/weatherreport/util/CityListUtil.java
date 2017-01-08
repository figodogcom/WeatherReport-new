package com.yzw.weather.weatherreport.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yzw.weather.weatherreport.model.City;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by yangzhiwei on 2016/10/9.
 */
public class CityListUtil {



    public static List<City> getChinaCityList(Context context) {
        return getCityList(context, "china-city-list.json");
    }

    public static List<City> getWorldTopCityList(Context context) {
        return getCityList(context, "world-top-city-list.json");
    }

    /**
     * @param key 关键字 如："海"
     * @return
     */
    public static List<City> searchCity(Context context, String key) {
        List<City> list = new ArrayList<>();

        if (TextUtils.isEmpty(key)) {
            return list;
        }

        List<City> chinaCityList = getChinaCityList(context);

        for (City city : chinaCityList) {
            if (city.cityZh.indexOf(key) != -1) {
                list.add(city);
            }
        }

//        int N = cityList.size();
//        for (int i = 0; i < N; i++) {
//            City city = cityList.get(i);
//            if (city.cityZh.indexOf(key) != -1) {
//                citylist.add(city);
//            }
//        }

        return list;
    }

    private static List<City> getCityList(Context context, String json) {

        InputStream inputStream = null;

        try {
            inputStream = context.getAssets().open(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<List<City>>() {
        }.getType();
        return gson.fromJson(new InputStreamReader(inputStream), listType);
    }
}
