package com.yzw.weather.weatherreport.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.db.Favourite;
import com.yzw.weather.weatherreport.db.Start;
import com.yzw.weather.weatherreport.preferences.WeatherPreference;
import com.yzw.weather.weatherreport.util.Utils;

/**
 * Created by yangzhiwei on 2016/12/16.
 */
public class CollectionItemView extends RelativeLayout {

    TextView cityTextView;
    TextView mainTempTextView;

    /**
     * cond_txt_d 天气预报的描述
     */
    TextView condTxtDTextView;
    TextView tmpMaxTextView;
    TextView tmpMinTextView;
    TextView windDirTextView;
    TextView humTextView;
    /**
     * sc 风速
     */
    TextView scTextView;
    TextView tempModeTextView;

    private WeatherPreference mWeatherPreference;

    public CollectionItemView(Context context) {
        super(context);
        init();
    }

    public CollectionItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CollectionItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CollectionItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init() {
        inflate(getContext(), R.layout.item_collection, this);

        cityTextView = (TextView) findViewById(R.id.tv_city);
        mainTempTextView = (TextView) findViewById(R.id.tv_main_temp);
        condTxtDTextView = (TextView) findViewById(R.id.tv_cond_txt_d);
        tmpMaxTextView = (TextView) findViewById(R.id.tv_tmp_max);
        tmpMinTextView = (TextView) findViewById(R.id.tv_tmp_min);
        windDirTextView = (TextView) findViewById(R.id.tv_wind_dir);
        humTextView = (TextView) findViewById(R.id.tv_hum);
        scTextView = (TextView) findViewById(R.id.tv_sc);
        tempModeTextView = (TextView) findViewById(R.id.tv_temp_mode);

    }


    public void bindStart(Start start) {
        if (start == null) {
            cityTextView.setText("--");

            condTxtDTextView.setText("--");

            windDirTextView.setText("--");

            humTextView.setText("--");
            scTextView.setText("--");


            tempModeTextView.setText("°C");
            mainTempTextView.setText("--");
            tmpMaxTextView.setText("--");
            tmpMinTextView.setText("--");

            return;
        }

        boolean isSheshi = mWeatherPreference.getTempertrueMode().equals(WeatherPreference.TempertureMode.SHESHI.getMode());

        Log.i("rrrrr", "start:" + start.toString());
        cityTextView.setText(start.getCityZh());

        condTxtDTextView.setText(start.getCond_txt_d());

        windDirTextView.setText(start.getWind_dir());

        humTextView.setText(start.getHum() + "%");
        scTextView.setText(start.getSc() + "里/时");

        if (isSheshi)

        {
            tempModeTextView.setText("°C");
            mainTempTextView.setText(start.getTmp());
            tmpMaxTextView.setText(start.getTmp_max() + "°");
            tmpMinTextView.setText(start.getTmp_min() + "°");
        } else

        {
            tempModeTextView.setText("°F");
            mainTempTextView.setText(Utils.c2f(start.getTmp()));
            tmpMaxTextView.setText(Utils.c2f(start.getTmp_max()) + "°");
            tmpMinTextView.setText(Utils.c2f(start.getTmp_min()) + "°");


        }


    }

    public void bindFavourite(Favourite favourite) {
        cityTextView.setText(favourite.getCityZh());
        mainTempTextView.setText(favourite.getTmp());
        condTxtDTextView.setText(favourite.getCond_txt_d());
        tmpMaxTextView.setText(favourite.getTmp_max() + "°");
        tmpMinTextView.setText(favourite.getTmp_min() + "°");
        windDirTextView.setText(favourite.getWind_dir());
        scTextView.setText(favourite.getSc() + "级");
        humTextView.setText(favourite.getHum() + "%");

        boolean isSheshi = mWeatherPreference.getTempertrueMode().equals(WeatherPreference.TempertureMode.SHESHI.getMode());

        if (isSheshi) {
            tempModeTextView.setText("°C");

        } else {
            tempModeTextView.setText("°F");
            mainTempTextView.setText(Utils.c2f(favourite.getTmp()));
            tmpMaxTextView.setText(Utils.c2f(favourite.getTmp_max()) + "°");
            tmpMinTextView.setText(Utils.c2f(favourite.getTmp_min()) + "°");
        }
    }


}
