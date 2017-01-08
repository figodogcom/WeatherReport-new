package com.yzw.weather.weatherreport.response.suggestion;

import com.yzw.weather.weatherreport.response.suggestion.Air;
import com.yzw.weather.weatherreport.response.suggestion.Comf;
import com.yzw.weather.weatherreport.response.suggestion.Cw;
import com.yzw.weather.weatherreport.response.suggestion.Drsg;
import com.yzw.weather.weatherreport.response.suggestion.Flu;
import com.yzw.weather.weatherreport.response.suggestion.Sport;
import com.yzw.weather.weatherreport.response.suggestion.Trav;
import com.yzw.weather.weatherreport.response.suggestion.Uv;

/**
 * Created by yangzhiwei on 2016/12/7.
 */
public class Suggestion {
    public Air air;
    public Comf comf;
    public Cw cw;
    public Drsg drsg;
    public Flu flu;
    public Sport sport;
    public Trav trav;
    public Uv uv;

    @Override
    public String toString() {
        return "Suggestion{" +
                "air=" + air +
                ", comf=" + comf +
                ", cw=" + cw +
                ", drsg=" + drsg +
                ", flu=" + flu +
                ", sport=" + sport +
                ", trav=" + trav +
                ", uv=" + uv +
                '}';
    }
}
