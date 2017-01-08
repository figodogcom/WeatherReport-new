package com.yzw.weather.weatherreport.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.fragment.SearchFragment;
import com.yzw.weather.weatherreport.model.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangzhiwei on 2016/12/9.
 */
public class Utils {
    public static Bitmap getIconResId(String icon, Context context) {
        Bitmap bit = null;
        try {
            bit = BitmapFactory.decodeStream(context.getAssets().open("hefeng/"+ icon + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  bit;
    }

    public static String GetNetIp() {
        InputStream inStream = null;
        String ipLine = "";
        HttpURLConnection httpConnection = null;
        try {
            URL infoUrl = new URL("http://1212.ip138.com/ic.asp");
            URLConnection connection = infoUrl.openConnection();
            Log.i("UUUUU","connection:" + connection);
            httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            Log.i("TTTTT", "responseCode:" + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                    strber.append(line + "\n");

                Pattern pattern = Pattern
                        .compile("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)");
                Matcher matcher = pattern.matcher(strber.toString());
                if (matcher.find()) {
                    ipLine = matcher.group();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (httpConnection != null) {
                    httpConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ipLine;
    }

    public static void cancelTaskInterrupt(AsyncTask<?, ?, ?> task) {
        cancelTask(task, true);
    }

    public static void cancelTask(AsyncTask<?, ?, ?> task, boolean interrupt) {
        if (task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
            task.cancel(interrupt);
        }
    }

    public static String c2f (String strC){
        int intF = Integer.parseInt(strC);
        return String.valueOf(intF * 9 / 5 + 32);
    }
}
