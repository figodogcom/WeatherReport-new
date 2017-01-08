package com.yzw.weather.weatherreport.response;

/**
 * Created by yangzhiwei on 2016/12/6.
 */
public class Wind {
    /**
     * 风向(360度)
     */
    public String deg;
    /**
     * 风向
     */
    public String dir;
    /**
     * 风力
     */
    public String sc;
    /**
     * 风速（kmph）
     */
    public String spd;

    public String getDegDesc(){
        int deg = Integer.valueOf(this.deg).intValue();;

        if (deg <= 22.5 || deg >= 337.5) {
            return "北风";
        } else if (deg <= 67.5) return "东北风";
        else if (deg <= 112.5) return "东风";
        else if (deg <= 157.5) return "东南风";
        else if (deg <= 202.5) return "南风";
        else if (deg <= 247.5) return "西南风";
        else if (deg <= 292.5) return "西风";
        else if (deg <= 337.5) return "西北风";

        return "未知";

    }

    @Override
    public String toString() {
        return "Wind{" +
                "deg='" + deg + '\'' +
                ", dir='" + dir + '\'' +
                ", sc='" + sc + '\'' +
                ", spd='" + spd + '\'' +
                '}';
    }
}
