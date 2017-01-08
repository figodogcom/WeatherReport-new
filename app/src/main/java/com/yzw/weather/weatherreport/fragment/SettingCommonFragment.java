package com.yzw.weather.weatherreport.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.preferences.WeatherPreference;
import com.yzw.weather.weatherreport.util.Utils;
import com.yzw.weather.weatherreport.view.SettingSearchView;


/**
 * Created by yangzhiwei on 2016/10/26.
 */
public class SettingCommonFragment extends Fragment {
    //    RelativeLayout searchRelativeLayout;
    SettingSearchView mSettingSearchView;

    WeatherPreference mWeatherPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_setting_common, container, false);
        RadioButton rb_huashi = (RadioButton) rootview.findViewById(R.id.rb_huashi);
        RadioButton rb_sheshi = (RadioButton) rootview.findViewById(R.id.rb_sheshi);
        RadioButton rb_default = (RadioButton) rootview.findViewById(R.id.rb_default);
        TextView tv_tempertruemode = (TextView) rootview.findViewById(R.id.tv_tempertruemode);
        TextView tv_location = (TextView) rootview.findViewById(R.id.tv_location);

        TextPaint paint1 = tv_tempertruemode.getPaint();
        TextPaint paint2 = tv_location.getPaint();

        paint1.setFakeBoldText(true);
        paint2.setFakeBoldText(true);

        final RadioButton mRadioButton_location = (RadioButton) rootview.findViewById(R.id.rb_location);
        mSettingSearchView = (SettingSearchView) rootview.findViewById(R.id.searchView);

        mWeatherPreference = new WeatherPreference(getContext());
        mWeatherPreference.restore();

        if (mWeatherPreference.tempertrueMode.equals(WeatherPreference.TempertureMode.SHESHI.getMode()))
            rb_sheshi.setChecked(true);
        else rb_huashi.setChecked(true);

        if (mWeatherPreference.predictionCityMode.equals(WeatherPreference.PredictionCityMode.DEFAULT.getMode())) {
            rb_default.setChecked(true);
            changeColor("default");
        } else {
            mRadioButton_location.setChecked(true);
            changeColor("location");
        }


        rb_huashi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
//                    editor.putString("tempertruemode", "huashi");
//                    editor.commit();
                    mWeatherPreference.setTempertrueMode(WeatherPreference.TempertureMode.HUASHI.getMode());
                }
            }
        });

        rb_sheshi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
//                    editor.putString("tempertruemode", "sheshi");
//                    editor.commit();
                    mWeatherPreference.setTempertrueMode(WeatherPreference.TempertureMode.SHESHI.getMode());
                }
            }
        });


        rb_default.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
//                    editor.putString("predictionCityMode", "default");
                    GetNetIpTask mGetNetIpTask = new GetNetIpTask();
                    mGetNetIpTask.execute();


                    changeColor("default");
                }
            }
        });


        mRadioButton_location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.i("aaaa", String.valueOf(b));

                if (b) {
//                    editor.putString("predictionCityMode", "location");
//                    editor.commit();
                    mWeatherPreference.setPredictionCityMode(WeatherPreference.PredictionCityMode.LOCATION.getMode());
                    changeColor("location");
                }
            }
        });

        rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideInput(getContext(), rootview);
//                mSettingSearchView.setFocusable(false);
            }
        });


        return rootview;
    }

    void changeColor(String status) {
        Log.i("eeeee", status);
        if (status.equals("default")) {
            mSettingSearchView.setEnabled(false);
//            mSettingSearchView.setBackgroundColor(Color.parseColor("#999999"));
        } else {
//            mSettingSearchView.setBackgroundColor(Color.parseColor("#CCCCCC"));
            mSettingSearchView.setEnabled(true);
        }

    }


    /**
     * 强制隐藏输入法键盘
     */
    private void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private class GetNetIpTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
//            super.onPreExecute();   可操作ui
        }

        @Override
        protected String doInBackground(Void... voids) {
            return Utils.GetNetIp();
        }

        @Override
        protected void onPostExecute(String ip) {
//            super.onPostExecute(s);   可操作ui

            mWeatherPreference.restore();
            mWeatherPreference.defaultCity = WeatherPreference.PredictionCityMode.DEFAULT.getMode();
            mWeatherPreference.defaultCity = ip;
            mWeatherPreference.save();
//            editor.putString("default_city", ip);
//            editor.commit();


        }
    }


}
