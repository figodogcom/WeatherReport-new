package com.yzw.weather.weatherreport.response;

/**
 * Created by yangzhiwei on 2016/12/7.
 */
public class DailyForecast {
    public Astro astro;
    public DailyForecastCond cond;
    public String date;
    public String hum;
    public String pcpn;
    public String pop;
    public String pres;
    public Tmp tmp;
    public String uv;
    public String vis;
    public Wind wind;

    @Override
    public String toString() {
        return "Daily_forecast{" +
                "astro=" + astro +
                ", cond=" + cond +
                ", date='" + date + '\'' +
                ", hum='" + hum + '\'' +
                ", pcpn='" + pcpn + '\'' +
                ", pop='" + pop + '\'' +
                ", pres='" + pres + '\'' +
                ", tmp=" + tmp +
                ", uv='" + uv + '\'' +
                ", vis='" + vis + '\'' +
                ", wind=" + wind +
                '}';
    }
}
