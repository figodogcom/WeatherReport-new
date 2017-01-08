package com.yzw.weather.weatherreport.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.adapter.AutoCompleteAdapter;
import com.yzw.weather.weatherreport.preferences.WeatherPreference;

/**
 * Created by yangzhiwei on 2016/12/29.
 */
public class SettingSearchView extends RelativeLayout {

    private AutoCompleteTextView mAutoCompleteTextView;
    private ImageView cancelView;

    private WeatherPreference mWeatherPreference;

    public SettingSearchView(Context context) {
        super(context);
        init();
    }

    public SettingSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SettingSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SettingSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.fragment_setting_searchview, this);

        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.actv_cityname);
        cancelView = (ImageView) findViewById(R.id.btn_cancel);

        cancelView.setVisibility(INVISIBLE);
        final AutoCompleteAdapter mAutoCompleteAdapter = new AutoCompleteAdapter(getContext());
        mAutoCompleteTextView.setAdapter(mAutoCompleteAdapter);

        mWeatherPreference = new WeatherPreference(getContext());

        mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("beforeTextChanged", String.valueOf(s));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("onTextChanged", String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("afterTextChanged", String.valueOf(s));
                if(s.length()==0)
                    cancelView.setVisibility(INVISIBLE);
                else
                    cancelView.setVisibility(VISIBLE);

            }
        });


        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                mWeatherPreference.setDefaultCity((String) adapterView.getItemAtPosition(position));

                Log.i("bbbbb","default_city:" + mWeatherPreference.getDefaultCity());
            }
        });

        cancelView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mAutoCompleteTextView.setText(null);
            }
        });

    }

    @Override
    public void setEnabled(boolean enabled) {

        super.setEnabled(enabled);
        mAutoCompleteTextView.setEnabled(enabled);




    }



    //    @Override
//    public void setBackgroundColor(int color) {
//        super.setBackgroundColor(color);
//        searchView.setBackgroundColor(color);
//        cancelView.setBackgroundColor(color);
//    }
}
