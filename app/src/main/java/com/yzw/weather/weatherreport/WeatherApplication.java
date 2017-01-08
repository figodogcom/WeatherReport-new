package com.yzw.weather.weatherreport;

import android.app.Application;

import com.yzw.weather.weatherreport.api.WeatherService;
import com.yzw.weather.weatherreport.db.DaoMaster;
import com.yzw.weather.weatherreport.db.DaoSession;

import org.greenrobot.greendao.database.Database;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yangzhiwei on 2016/12/7.
 */
public class WeatherApplication extends Application {

//    private static WeatherApplication sInstance;
//
//    public static WeatherApplication getInstance() {
//        return sInstance;
//    }
    public static final boolean ENCRYPTED = false;

    private WeatherService service;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

//        sInstance = this;
    }

    public WeatherService getService() {

        if (service == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder = builder.addInterceptor(interceptor);
//            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://free-api.heweather.com/v5/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(WeatherService.class);
        }

        return service;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
