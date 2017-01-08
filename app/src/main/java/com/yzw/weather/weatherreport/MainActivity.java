package com.yzw.weather.weatherreport;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;

import com.yzw.weather.weatherreport.adapter.DownLeftMenuAdapter;
import com.yzw.weather.weatherreport.adapter.UpLeftMenuAdapter;
import com.yzw.weather.weatherreport.fragment.CollectionFragment;
import com.yzw.weather.weatherreport.fragment.PredictionFragment;
import com.yzw.weather.weatherreport.fragment.SettingFragment;
import com.yzw.weather.weatherreport.preferences.WeatherPreference;

public class MainActivity extends AppCompatActivity implements UpLeftMenuAdapter.UpLeftMenuAdapterCallback, DownLeftMenuAdapter.DownLeftMenuAdapterCallback, CollectionFragment.CollectionFragmentCallback {

    private static final String TAG = "okokok";

    private DrawerLayout drawer;
    private RelativeLayout mRelativeLayout;

    private UpLeftMenuAdapter mUpLeftmenuAdapter;
    private DownLeftMenuAdapter mDownLeftmenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.leftmenu);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_left_recyclerview);
        mUpLeftmenuAdapter = new UpLeftMenuAdapter(this);
//        mUpLeftmenuAdapter.setUpLeftMenuAdapterCallback(this);
        mRecyclerView.setAdapter(mUpLeftmenuAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        RecyclerView mRecyclerView2 = (RecyclerView) findViewById(R.id.recycler_left_recyclerview2);
        mDownLeftmenuAdapter = new DownLeftMenuAdapter(this);
        mRecyclerView2.setAdapter(mDownLeftmenuAdapter);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));


        if (savedInstanceState == null) {
            selectItem(0);
        }


    }

    public void selectItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
//                fragment = PredictionFragment.newInstance("CN101280701");

                WeatherPreference preference = new WeatherPreference(this);
                Log.i("cccccc","default_city:" + preference.getDefaultCity());
                fragment = PredictionFragment.newInstance(preference.getDefaultCity());
                break;
            case 1:
                fragment = CollectionFragment.newInstance();
                break;
//
//            case 2:
//                break;
//
            case 5:
                fragment = new SettingFragment();
                break;

            default:
                break;
        }
        if (fragment == null)
            return;
        replaceFragment(fragment);
        drawer.closeDrawer(mRelativeLayout);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_view, fragment);
        ft.commit();
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.toggle_item, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // action with ID action_refresh was selected
//            case R.id.action_refresh:
//                selectItem(0);
//                break;
//            // action with ID action_settings was selected
//            case R.id.action_search:
//
//                break;
//            default:
//                break;
//        }
//        return true;
//    }


    @Override
    public void onUpLeftMenuAdapterItemSelected(int position) {
        mDownLeftmenuAdapter.setCurrentPosition(-1);
        selectItem(position);
    }


    @Override
    public void onDownLeftMenuAdapterItemSelected(int position) {
        mUpLeftmenuAdapter.setCurrentPosition(-1);
        int realposition = position + 4;
        selectItem(realposition);
    }

    @Override
    public void onCollectionFragmentClickCity(String cityid) {
        mUpLeftmenuAdapter.setCurrentPosition(0);
        Fragment fragment = PredictionFragment.newInstance(cityid);
        replaceFragment(fragment);
    }
}
