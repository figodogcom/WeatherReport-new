package com.yzw.weather.weatherreport.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.adapter.MyPageAdapter;

import java.util.ArrayList;

/**
 * Created by yangzhiwei on 2016/12/28.
 */
public class SettingFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        String[] tabString = {"通用", "关于"};
        ArrayList<Fragment> mFragments = new ArrayList<>();

        Fragment mSettingCommonFragment = new SettingCommonFragment();
        Fragment mSettingAboutFragment = new SettingAboutFragment();
        mFragments.add(mSettingCommonFragment);
        mFragments.add(mSettingAboutFragment);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        MyPageAdapter mViewPagerAdapter = new MyPageAdapter(getActivity().getSupportFragmentManager(), tabString, mFragments);
        viewPager.setAdapter(mViewPagerAdapter);
//将Tab与ViewPager关联（动画效果同步）
        tabLayout.setupWithViewPager(viewPager);



        return rootView;
    }
}
