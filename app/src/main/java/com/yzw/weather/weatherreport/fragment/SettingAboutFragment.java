package com.yzw.weather.weatherreport.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzw.weather.weatherreport.R;

/**
 * Created by yangzhiwei on 2016/12/28.
 */
public class SettingAboutFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_setting_about,container,false);
        return rootview;
    }
}
