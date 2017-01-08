package com.yzw.weather.weatherreport.response;

/**
 * Created by yangzhiwei on 2016/12/7.
 */
public class DailyForecastCond {
    public String code_d;
    public String Code_n;
    public String txt_d;
    public String txt_n;

    @Override
    public String toString() {
        return "Daily_forecast_Cond{" +
                "code_d='" + code_d + '\'' +
                ", Code_n='" + Code_n + '\'' +
                ", txt_d='" + txt_d + '\'' +
                ", txt_n='" + txt_n + '\'' +
                '}';
    }
}
