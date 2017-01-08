package com.yzw.weather.weatherreport.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yzw.weather.weatherreport.R;

/**
 * Created by yangzhiwei on 2017/1/6.
 */
public class HourlyForecastItemView extends LinearLayout{
    public HourlyForecastItemView(Context context) {
        super(context);
        init();
    }

    public HourlyForecastItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HourlyForecastItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public HourlyForecastItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){
        inflate(getContext(), R.layout.item_hourly_forecast,this);

    }
}
