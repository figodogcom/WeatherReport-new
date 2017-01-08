package com.yzw.weather.weatherreport.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.WeatherApplication;
import com.yzw.weather.weatherreport.api.WeatherService;
import com.yzw.weather.weatherreport.preferences.WeatherPreference;
import com.yzw.weather.weatherreport.response.ReaponseWeather;
import com.yzw.weather.weatherreport.util.Constances;
import com.yzw.weather.weatherreport.util.Utils;
import com.yzw.weather.weatherreport.view.DailyForecastItemView;
import com.yzw.weather.weatherreport.view.HourlyForecastView;
import com.yzw.weather.weatherreport.view.NowView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangzhiwei on 2016/12/9.
 */
public class PredictionFragment extends Fragment implements NowView.NowViewCallback {
    private static final String TAG = "YZW";

    private static final String ARG_CITY = "city";

    private String mCity;

    private Call<ReaponseWeather> mCallWeather;

    ReaponseWeather mResponseWeather;

    ProgressBar mProgressBar;
    //    boolean oneday_requset;
//    boolean sevenday_request;
    NowView mNowView;
    RelativeLayout msevendayview;

    private GetNetIpTask mGetNetIpTask;

    /**
     *
     * @param city cityId cityName IP
     * @return
     */
    public static Fragment newInstance(String city) {
        Fragment fragment = new PredictionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = getArguments().getString(ARG_CITY);
        }

        setHasOptionsMenu(true);  //确保操作toolbar菜单生效
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prediction, container, false);

        mNowView = (NowView) rootView.findViewById(R.id.nowView);
        msevendayview = (RelativeLayout) rootView.findViewById(R.id.sevenday_view);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.prediction_progressbar);

        mNowView.setOnNowViewCallback(this);

        mNowView.setVisibility(View.INVISIBLE);
        msevendayview.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        RelativeLayout mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.rl_mainlayout);
        HourlyForecastView mHourlyForecastView = new HourlyForecastView(getContext());
        mRelativeLayout.addView(mHourlyForecastView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        receive_location();

//        receive_location2(getContext());

        if(mCity == null){
            mGetNetIpTask = new GetNetIpTask();
            mGetNetIpTask.execute();
        }else {
            weather();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mCallWeather != null) {
            mCallWeather.cancel();
        }

        if (mGetNetIpTask != null && mGetNetIpTask.getStatus() != AsyncTask.Status.FINISHED) {
            mGetNetIpTask.cancel(true);
        }
    }

    private void weather() {
//        DecimalFormat decimalFormat = new DecimalFormat("###.###");



        WeatherService service = ((WeatherApplication) getActivity().getApplication()).getService();
        mCallWeather = service.weather(Constances.API_KEY, mCity);

        mCallWeather.enqueue(new Callback<ReaponseWeather>() {
            @Override
            public void onResponse(Call<ReaponseWeather> call, Response<ReaponseWeather> response) {

                mProgressBar.setVisibility(View.INVISIBLE);

                mResponseWeather = response.body();
                Log.d(TAG, "mResponseWeather: " + mResponseWeather);
                bindNowView(mResponseWeather);
                bindDailyForecast(mResponseWeather);
            }

            @Override
            public void onFailure(Call<ReaponseWeather> call, Throwable t) {
                Log.d(TAG, "call: " + call);
                Log.d(TAG, "t: " + t);

                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void bindNowView(final ReaponseWeather reaponseAll) {
        mNowView.setVisibility(View.VISIBLE);
        mNowView.bindData(reaponseAll);
    }

    private void bindDailyForecast(ReaponseWeather reaponseAll) {
        msevendayview.setVisibility(View.VISIBLE);

        View rootView = getView();
        LinearLayout scrollContentLayout = (LinearLayout) rootView.findViewById(R.id.scroll_content);
        TextView mTextView = (TextView) rootView.findViewById(R.id.one_textView11);
        mTextView.setText("每日");
        scrollContentLayout.removeAllViews();
        for (int i = 0; i < reaponseAll.HeWeather5.get(0).daily_forecast.size(); i++) {
            DailyForecastItemView dailyForecastItemView = new DailyForecastItemView(getContext());
            dailyForecastItemView.setIsFirst(i == 0);

            dailyForecastItemView.bindData(reaponseAll.HeWeather5.get(0).daily_forecast.get(i));
            scrollContentLayout.addView(dailyForecastItemView);
        }
    }

    @Override
    public void onNowViewChangeUnit(String unit) {
        bindNowView(mResponseWeather);
        bindDailyForecast(mResponseWeather);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toggle_item, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh:

                break;

            case R.id.action_search:

                break;
            default:
                break;
        }

        return true;
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
            Log.i("SSSSSS", "onPostExecute() ip" + ip);

            WeatherPreference preference = new WeatherPreference(getContext());
            preference.setDefaultCity(ip);

            if (isCancelled()) {
                return;
            }

            //
            if (TextUtils.isEmpty(ip)){
                Toast.makeText(getContext(),"获取ip地址失败",Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
                return;
            }

            mCity = ip;

            weather();

        }
    }





}
