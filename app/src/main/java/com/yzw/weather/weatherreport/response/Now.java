package com.yzw.weather.weatherreport.response;

/**
 * Created by yangzhiwei on 2016/12/6.
 */
public class Now {
    public Cond cond;
    /**
     * 体感温度
     */
    public String fl;
    /**
     * 相对湿度
     */
    public String hum;

    /**
     * 降水量
     */
    public String pcpn;

    /**
     *气压
     */
    public String pres;
    /**
     * 温度
     */
    public String tmp;
    /**
     * 能见度
     */
    public String vis;
    /**
     * 风力风向
     */
    public Wind wind;

    @Override
    public String toString() {
        return "Now{" +
                "cond=" + cond +
                ", fl='" + fl + '\'' +
                ", hum='" + hum + '\'' +
                ", pcpn='" + pcpn + '\'' +
                ", pres='" + pres + '\'' +
                ", tmp='" + tmp + '\'' +
                ", vis='" + vis + '\'' +
                ", wind=" + wind +
                '}';
    }
}
