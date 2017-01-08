package com.yzw.weather.weatherreport.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yangzhiwei on 2016/12/22.
 */
@Entity
public class Favourite {
    @Id
    private Long id;

    @NotNull
    private String cityid;

    @NotNull
    private String cityZh;
    /**
     * 白天天气状况描述
     */
    private String cond_txt_d;
    /**
     * 温度
     */
    private String tmp;
    /**
     * 风向
     */
    private String wind_dir;
    /**
     * 风力等级
     */
    private String sc;
    /**
     * 相对湿度（%）
     */
    private String hum;
    private String tmp_max;
    private String tmp_min;
    @Generated(hash = 1913067606)
    public Favourite(Long id, @NotNull String cityid, @NotNull String cityZh,
            String cond_txt_d, String tmp, String wind_dir, String sc, String hum,
            String tmp_max, String tmp_min) {
        this.id = id;
        this.cityid = cityid;
        this.cityZh = cityZh;
        this.cond_txt_d = cond_txt_d;
        this.tmp = tmp;
        this.wind_dir = wind_dir;
        this.sc = sc;
        this.hum = hum;
        this.tmp_max = tmp_max;
        this.tmp_min = tmp_min;
    }
    @Generated(hash = 1933414424)
    public Favourite() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCityid() {
        return this.cityid;
    }
    public void setCityid(String cityid) {
        this.cityid = cityid;
    }
    public String getCityZh() {
        return this.cityZh;
    }
    public void setCityZh(String cityZh) {
        this.cityZh = cityZh;
    }
    public String getCond_txt_d() {
        return this.cond_txt_d;
    }
    public void setCond_txt_d(String cond_txt_d) {
        this.cond_txt_d = cond_txt_d;
    }
    public String getTmp() {
        return this.tmp;
    }
    public void setTmp(String tmp) {
        this.tmp = tmp;
    }
    public String getWind_dir() {
        return this.wind_dir;
    }
    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }
    public String getSc() {
        return this.sc;
    }
    public void setSc(String sc) {
        this.sc = sc;
    }
    public String getHum() {
        return this.hum;
    }
    public void setHum(String hum) {
        this.hum = hum;
    }
    public String getTmp_max() {
        return this.tmp_max;
    }
    public void setTmp_max(String tmp_max) {
        this.tmp_max = tmp_max;
    }
    public String getTmp_min() {
        return this.tmp_min;
    }
    public void setTmp_min(String tmp_min) {
        this.tmp_min = tmp_min;
    }


}
