package com.yzw.weather.weatherreport.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.preferences.WeatherPreference;
import com.yzw.weather.weatherreport.response.HeWeather5;
import com.yzw.weather.weatherreport.response.ReaponseWeather;
import com.yzw.weather.weatherreport.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yangzhiwei on 2016/12/13.
 */
public class NowView extends RelativeLayout {

    TextView mCityNameTextView;
    TextView mTmpTextView;
    TextView mCondTxtTextView;
    ImageView mImageView;
    TextView mWindTextView;
    TextView mUpdateTextView;
    TextView mUnitUpTextView;
    TextView mUnitDownTextView;

    private NowViewCallback mNowViewCallback;

    private WeatherPreference mWeatherPreference;

    public NowView(Context context) {
        super(context);

        init(context);
    }

    public NowView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public NowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }

    private void init(Context context) {
        View rootView = inflate(context, R.layout.view_now, this);

        mCityNameTextView = (TextView) findViewById(R.id.tv_city_name);
        mTmpTextView = (TextView) findViewById(R.id.tv_tmp);
        mCondTxtTextView = (TextView) findViewById(R.id.tv_cond_txt);
        mImageView = (ImageView) findViewById(R.id.image);
        mWindTextView = (TextView) findViewById(R.id.tv_wind);
        mUpdateTextView = (TextView) findViewById(R.id.tv_update);
        mUnitUpTextView = (TextView) findViewById(R.id.tv_unit_up);
        mUnitDownTextView = (TextView) findViewById(R.id.tv_unit_down);

        mWeatherPreference = new WeatherPreference(getContext());

        mUnitDownTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String unit = mWeatherPreference.getTempertrueMode();

                unit = unit.equals(WeatherPreference.TempertureMode.SHESHI.getMode()) ? WeatherPreference.TempertureMode.HUASHI.getMode() : WeatherPreference.TempertureMode.SHESHI.getMode();

                mWeatherPreference.setTempertrueMode(unit);

                if (mNowViewCallback != null) {
                    mNowViewCallback.onNowViewChangeUnit(unit);
                }
            }
        });
    }

    public void bindData(ReaponseWeather responseWeather) {
        String unit = new WeatherPreference(getContext()).getTempertrueMode();

        HeWeather5 heWeather5 = responseWeather.HeWeather5.get(0);

        mCityNameTextView.setText(heWeather5.basic.city);

        if (unit.equals("sheshi")) {
            mTmpTextView.setText(heWeather5.now.tmp + "°");
            mUnitUpTextView.setText("C");
            mUnitDownTextView.setText("F");
            mWindTextView.setText("风速  " + heWeather5.now.wind.getDegDesc() + heWeather5.now.wind.sc + "级" + "    湿度  " + heWeather5.now.hum + "%");
        } else {
            mTmpTextView.setText((Integer.parseInt(heWeather5.now.tmp) * 9 / 5 + 32) + "°");
            mUnitUpTextView.setText("F");
            mUnitDownTextView.setText("C");
            mWindTextView.setText("风速  " + heWeather5.now.wind.getDegDesc() + heWeather5.now.wind.sc + "级" + "    湿度  " + heWeather5.now.hum + "%");
        }
        mCondTxtTextView.setText(heWeather5.now.cond.txt);

//        textView6.setText("风速");
//        textView7.setText(getString(R.string.wind_speed, (int) oneday.wind.speed));
//        textView8.setText("湿度");
//        textView9.setText(String.valueOf(oneday.main.humidity) + "%");


        Bitmap bit = Utils.getIconResId(heWeather5.now.cond.code, getContext());
        mImageView.setImageBitmap(bit);


        mUpdateTextView.setText(getTime(heWeather5.basic.update.loc));
    }

    private String getTime(String dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("最后更新时间 hh:mm");
        String strday = null;
        try {
            Date date = sdf.parse(dt);
            strday = sdf2.format(date);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return strday;
    }

    public void setOnNowViewCallback(NowViewCallback callback) {
        mNowViewCallback = callback;
    }

    public interface NowViewCallback {
        void onNowViewChangeUnit(String unit);
    }
}
