package com.yzw.weather.weatherreport.fragment;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.WeatherApplication;
import com.yzw.weather.weatherreport.adapter.CollectionAdapter;
import com.yzw.weather.weatherreport.adapter.SearchAdapter;
import com.yzw.weather.weatherreport.api.WeatherService;
import com.yzw.weather.weatherreport.db.DaoSession;
import com.yzw.weather.weatherreport.db.Favourite;
import com.yzw.weather.weatherreport.db.FavouriteDao;
import com.yzw.weather.weatherreport.model.City;
import com.yzw.weather.weatherreport.response.HeWeather5;
import com.yzw.weather.weatherreport.response.ReaponseWeather;
import com.yzw.weather.weatherreport.util.CityListUtil;
import com.yzw.weather.weatherreport.util.Constances;
import com.yzw.weather.weatherreport.util.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements SearchAdapter.SearchCityCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SQLiteDatabase db;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mlayoutManager;
    private SearchAdapter searchAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private SearchCityTask mSearchCityTask;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p/>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance() {
        Fragment fragment = new SearchFragment();

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_collection_search, container, false);
        final SearchView sv = (SearchView) rootView.findViewById(R.id.searchview_text);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.searchview_list);
        mlayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mlayoutManager);

        searchAdapter = new SearchAdapter(this);
        mRecyclerView.setAdapter(searchAdapter);

//        sv.setOnQueryTextListener();
        sv.setIconifiedByDefault(false);
        // 设置该SearchView显示搜索按钮
        sv.setSubmitButtonEnabled(true);
        // 设置该SearchView内默认显示的提示文本
        sv.setQueryHint("查找");

        // 为该SearchView组件设置事件监听器
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 用户输入字符时激发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("KKK", "KKK");
                searchCity(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // 实际应用中应该在该方法内执行实际查询
                // 此处仅使用Toast显示用户输入的查询内容

                Log.i("KKK", query);

                searchCity(query);
                return false;
            }
        });


        return rootView;
    }

    private void searchCity(String str) {

        Utils.cancelTaskInterrupt(mSearchCityTask);

        mSearchCityTask = new SearchCityTask(str);
        mSearchCityTask.execute();
    }

    private class SearchCityTask extends AsyncTask<Void, Void, List<City>> {

        private String key;

        public SearchCityTask(String key) {
            this.key = key;
        }

        @Override
        protected List<City> doInBackground(Void... voids) {
            return CityListUtil.searchCity(getContext(), key);
        }

        @Override
        protected void onPostExecute(List<City> cityList) {
//            super.onPostExecute(cities);
            if (isCancelled()) {
                return;
            }

            searchAdapter.changeData(cityList, getContext());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void searchcityclick(String cityid) {
        WeatherService service = ((WeatherApplication) getActivity().getApplication()).getService();
        Call<ReaponseWeather> mCallWeather = service.weather(Constances.API_KEY, cityid);

        mCallWeather.enqueue(new Callback<ReaponseWeather>() {
            @Override
            public void onResponse(Call<ReaponseWeather> call, Response<ReaponseWeather> response) {

                ReaponseWeather mResponseWeather = response.body();
                savedb(mResponseWeather.HeWeather5.get(0));
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<ReaponseWeather> call, Throwable t) {
                getActivity().finish();


            }
        });

    }

    void savedb(HeWeather5 heWeather5) {
        Activity activity = (Activity) getContext();
        Application application = activity.getApplication();
        DaoSession daoSession = ((WeatherApplication) application).getDaoSession();
        FavouriteDao favouriteDao = daoSession.getFavouriteDao();
        String cityid = heWeather5.basic.id;
        String cityZh = heWeather5.basic.city;
        String cond_txt_d = heWeather5.now.cond.txt;
        String tmp = heWeather5.now.tmp;
        String wind_dir = heWeather5.now.wind.dir;
        String sc = heWeather5.now.wind.sc;
        String hum = heWeather5.now.hum;
        String tmp_max = heWeather5.daily_forecast.get(0).tmp.max;
        String tmp_min = heWeather5.daily_forecast.get(0).tmp.min;
        Favourite favourite = new Favourite(null, cityid, cityZh, cond_txt_d, tmp, wind_dir, sc, hum, tmp_max, tmp_min);
        Log.i("ddd", "ddd");
        favouriteDao.insert(favourite);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
