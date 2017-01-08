package com.yzw.weather.weatherreport.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.preferences.WeatherPreference;
import com.yzw.weather.weatherreport.response.DailyForecast;
import com.yzw.weather.weatherreport.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yangzhiwei on 2016/12/13.
 */
public class DailyForecastItemView extends LinearLayout{
    View mRootView;
    TextView mDateTextView;
    ImageView mImageView;
    TextView mTmpMaxTextView;
    TextView mTmpMinTextView;
    TextView mCondTxtTextView;

    Date mDate;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DailyForecastItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public DailyForecastItemView(Context context) {
        super(context);
        init(context);
    }

    public DailyForecastItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DailyForecastItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context){
        mRootView = inflate(context, R.layout.item_daily_forecast, this);
        mRootView.setClickable(true);
        mDateTextView = (TextView) findViewById(R.id.tv_date);
        mImageView = (ImageView) findViewById(R.id.image);
        mTmpMaxTextView = (TextView) findViewById(R.id.tv_tmp_max);
        mTmpMinTextView = (TextView) findViewById(R.id.tv_tmp_min);
        mCondTxtTextView = (TextView)findViewById(R.id.tv_cond_txt_d);


    }

    public void bindData(final DailyForecast item) {

        mCondTxtTextView.setText(item.cond.txt_d);

        // TODO 尝试使用Glide加载assets的图片
        Bitmap bit = Utils.getIconResId(item.cond.code_d, getContext());
        mImageView.setImageBitmap(bit);

        mDateTextView.setText(getdate(item.date));

        WeatherPreference preference = new WeatherPreference(getContext());
        if (preference.getTempertrueMode().equals(WeatherPreference.TempertureMode.SHESHI.getMode())) {
            mTmpMaxTextView.setText(item.tmp.max + "°");
            mTmpMinTextView.setText(item.tmp.min + "°");
        } else {
            mTmpMaxTextView.setText(String.valueOf(( Integer.parseInt(item.tmp.max) * 9 / 5 + 32)) + "°");
            mTmpMinTextView.setText(String.valueOf(( Integer.parseInt(item.tmp.min) * 9 / 5 + 32)) + "°");
        }
    }

    private String getdate(String dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat(getResources().getString(R.string.item_daily_title_pattern));

        try {
            Date date = sdf.parse(dt);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setIsFirst(boolean isFirst) {
        if (isFirst) {

            mRootView.setBackgroundResource(R.drawable.boder);
        } else {
            mRootView.setBackgroundResource(0);
        }
    }


}
