package com.yzw.weather.weatherreport.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzw.weather.weatherreport.R;

import com.yzw.weather.weatherreport.SearchActivity;
import com.yzw.weather.weatherreport.WeatherApplication;
import com.yzw.weather.weatherreport.db.DaoSession;
import com.yzw.weather.weatherreport.db.Favourite;
import com.yzw.weather.weatherreport.db.FavouriteDao;

import com.yzw.weather.weatherreport.fragment.SearchFragment;
import com.yzw.weather.weatherreport.model.City;

import org.greenrobot.greendao.query.Query;

import java.util.List;


/**
 * Created by yangzhiwei on 2016/9/30.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<City> citylist;

    private Context context;
//    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private Query<Favourite> notesQuery;
    private FavouriteDao favouriteDao;

    private SearchCityCallback searchCityCallback;

    public SearchAdapter(SearchCityCallback searchCityCallback){
        this.searchCityCallback = searchCityCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_collection_search_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final City city = citylist.get(position);
        Log.i("1111","holder" + holder);
        Log.i("2222","holder.cityname" + holder.cityname);
        Log.i("3333","city.name" + city.cityZh);

        holder.cityname.setText(city.cityZh);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchCityCallback.searchcityclick(city.id);
//                insertdb(city);


            }
        });

    }


        public SearchAdapter() {
        
    }

    public SearchAdapter(List<City> citylist) {
        this.citylist = citylist;

    }

    public void changeData(List<City> cityList,Context context) {
        this.citylist = cityList;
        this.context = context;
        Log.i("QQQ", "citylist:" + cityList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView cityname;

        public ViewHolder(View itemView) {
            super(itemView);
            cityname = (TextView) itemView.findViewById(R.id.search_result);



        }


    }


    @Override
    public int getItemCount() {


        return citylist == null ? 0 : citylist.size();
    }

    public interface SearchCityCallback{
        void searchcityclick(String cityid);

    }
}
