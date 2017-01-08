package com.yzw.weather.weatherreport.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.WeatherApplication;
import com.yzw.weather.weatherreport.adapter.CollectionAdapter;
import com.yzw.weather.weatherreport.api.WeatherService;
import com.yzw.weather.weatherreport.db.DaoSession;
import com.yzw.weather.weatherreport.db.Favourite;
import com.yzw.weather.weatherreport.db.FavouriteDao;
import com.yzw.weather.weatherreport.db.Start;
import com.yzw.weather.weatherreport.db.StartDao;
import com.yzw.weather.weatherreport.preferences.WeatherPreference;
import com.yzw.weather.weatherreport.response.HeWeather5;
import com.yzw.weather.weatherreport.response.ReaponseWeather;
import com.yzw.weather.weatherreport.util.Constances;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangzhiwei on 2016/12/16.
 */
public class CollectionFragment extends Fragment implements CollectionAdapter.CollectionAdapterCallback {

    private CollectionAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private CollectionFragmentCallback mListener;

    private LoadDataTask mLoadDataTask;

    public static Fragment newInstance() {
        Fragment fragment = new CollectionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collection,
                container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        setstartcity();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("AAA", "AAA");
        loadData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CollectionFragmentCallback) {
            mListener = (CollectionFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void loadData() {


        mLoadDataTask = new LoadDataTask();
        mLoadDataTask.execute();

    }

    private class LoadDataTask extends AsyncTask<Void, Void, Boolean> {

        private Start start;
        private List<Favourite> favouriteList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            DaoSession daoSession = ((WeatherApplication) getActivity().getApplication()).getDaoSession();
            FavouriteDao favouriteDao = daoSession.getFavouriteDao();
            StartDao startDao = daoSession.getStartDao();

            start = startDao.queryBuilder().limit(1).build().unique();
            favouriteList = favouriteDao.queryBuilder().build().list();

            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {

            if (isCancelled()) {
                return;
            }

            mAdapter = new CollectionAdapter(CollectionFragment.this, this.start, this.favouriteList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    void setstartcity() {
        WeatherService service = ((WeatherApplication) getActivity().getApplication()).getService();


        WeatherPreference preference = new WeatherPreference(getContext());
        String city = preference.getDefaultCity();
        if (city == null) {
            city = "CN101010100";
        }

        Log.i("tttttt", "default_city:" + city);
        Call<ReaponseWeather> mCallWeather = service.weather(Constances.API_KEY, city);

        mCallWeather.enqueue(new Callback<ReaponseWeather>() {
            @Override
            public void onResponse(Call<ReaponseWeather> call, Response<ReaponseWeather> response) {

                ReaponseWeather mResponseWeather = response.body();
                savedb(mResponseWeather.HeWeather5.get(0));
                mLoadDataTask = new LoadDataTask();
                mLoadDataTask.execute();
//                loadData();
            }

            @Override
            public void onFailure(Call<ReaponseWeather> call, Throwable t) {

            }
        });


    }

    private void savedb(HeWeather5 heWeather5) {
        Activity activity = (Activity) getContext();
        Application application = activity.getApplication();
        DaoSession daoSession = ((WeatherApplication) application).getDaoSession();
        StartDao startDaoDao = daoSession.getStartDao();
        String cityid = heWeather5.basic.id;
        String cityZh = heWeather5.basic.city;
        String cond_txt_d = heWeather5.now.cond.txt;
        String tmp = heWeather5.now.tmp;
        String wind_dir = heWeather5.now.wind.dir;
        String sc = heWeather5.now.wind.sc;
        String hum = heWeather5.now.hum;
        String tmp_max = heWeather5.daily_forecast.get(0).tmp.max;
        String tmp_min = heWeather5.daily_forecast.get(0).tmp.min;
        Start start = new Start(null, cityid, cityZh, cond_txt_d, tmp, wind_dir, sc, hum, tmp_max, tmp_min);
        Log.i("ddd", "ddd");
        startDaoDao.deleteAll();
        startDaoDao.insert(start);

    }

    @Override
    public void onCollectionAdapterClickCity(String cityid) {
        if (mListener != null) {
            mListener.onCollectionFragmentClickCity(cityid);
        }
    }

    @Override
    public void onCollectionAdapterDeleteCity(Favourite favourite) {
        FavouriteDao favouriteDao = ((WeatherApplication) getActivity().getApplication()).getDaoSession().getFavouriteDao();
        favouriteDao.delete(favourite);

        mLoadDataTask = new LoadDataTask();
        mLoadDataTask.execute();
//                loadData();
    }

    public interface CollectionFragmentCallback {
        void onCollectionFragmentClickCity(String cityid);
    }
}
