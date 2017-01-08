package com.yzw.weather.weatherreport.model;

import java.util.List;

/**
 * Created by yangzhiwei on 2016/12/20.
 */
@Deprecated
public class CityList {
    public List<City> citylist;

    @Override
    public String toString() {
        return "CityList{" +
                "citylist=" + citylist +
                '}';
    }
}
