package com.yzw.weather.weatherreport.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzw.weather.weatherreport.R;

/**
 * Created by yangzhiwei on 2017/1/7.
 */
public class HourlyForecastView extends LinearLayout{
    public HourlyForecastView(Context context) {
        super(context);
        init();
    }

    public HourlyForecastView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HourlyForecastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HourlyForecastView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init(){
        inflate(getContext(),  R.layout.fragment_prediction_hourly,this);
        ImageView mimageView = (ImageView) findViewById(R.id.im_view);
        TextView mTempertrue = (TextView) findViewById(R.id.tv_tempertrue);
        TextView mdescription = (TextView) findViewById(R.id.tv_description);
        TextView mtime = (TextView) findViewById(R.id.tv_time);
    }

    public void binddata(){

    }

}
