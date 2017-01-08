package com.yzw.weather.weatherreport.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yangzhiwei on 2016/12/13.
 */
public class WeatherPreference {

    public static String SHARED_PREFERENCE_NAME = "weather";

    private SharedPreferences mSharedPreferences;

    public static String KEY_TEMPERTRUEMODE = "tempertruemode";
    public static String KEY_PREDICTIONCITYMODE = "predictionCityMode";
    public static String KEY_DEFAULT_CITY = "default_city";

//    public static String TEMPERTRUEMODE_SHESHI = "sheshi";
//    public static String TEMPERTRUEMODE_HUASHI = "huashi";

    /**
     * TempertureMode sheshi huashi
     */
    public static enum TempertureMode{

        SHESHI("sheshi"), HUASHI("huashi");

        private String mode;

        TempertureMode(String mode){
            this.mode = mode;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }
    }

    public static enum PredictionCityMode{

        DEFAULT("default"), LOCATION("location");

        private String mode;

        PredictionCityMode(String mode){
            this.mode = mode;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }
    }

    /**
     * huashi
     * sheshi
     */
    public String tempertrueMode;

    /**
     * default
     * location
     */
    public String predictionCityMode;

    /**
     * ip
     * cityId
     * cityName
     */
    public String defaultCity;

    public WeatherPreference(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public String getTempertrueMode() {
        tempertrueMode = mSharedPreferences.getString(KEY_TEMPERTRUEMODE, TempertureMode.SHESHI.getMode());
        return tempertrueMode;
    }

    public void setTempertrueMode(String tempertrueMode) {
        this.tempertrueMode = tempertrueMode;
        putString(KEY_TEMPERTRUEMODE, tempertrueMode);
    }

    public String getPredictionCityMode() {
        predictionCityMode = mSharedPreferences.getString(KEY_PREDICTIONCITYMODE, PredictionCityMode.DEFAULT.getMode());
        return predictionCityMode;
    }

    public void setPredictionCityMode(String predictionCityMode) {
        this.predictionCityMode = predictionCityMode;
        putString(KEY_PREDICTIONCITYMODE, predictionCityMode);
    }

    public String getDefaultCity() {
        defaultCity = mSharedPreferences.getString(KEY_DEFAULT_CITY, null);
        return defaultCity;
    }

    public void setDefaultCity(String default_city) {
        this.defaultCity = default_city;
        putString(KEY_DEFAULT_CITY, default_city);
    }



    public void save() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putString(KEY_TEMPERTRUEMODE, tempertrueMode);
        editor.putString(KEY_PREDICTIONCITYMODE, predictionCityMode);
        editor.putString(KEY_DEFAULT_CITY, defaultCity);

        editor.commit();
    }

    public void restore() {
        this.tempertrueMode = mSharedPreferences.getString(KEY_TEMPERTRUEMODE, TempertureMode.SHESHI.getMode());
        this.predictionCityMode = mSharedPreferences.getString(KEY_PREDICTIONCITYMODE, PredictionCityMode.DEFAULT.getMode());
        this.defaultCity = mSharedPreferences.getString(KEY_DEFAULT_CITY, null);
    }

    private void putString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
