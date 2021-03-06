package com.yzw.weather.weatherreport.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.model.City;
import com.yzw.weather.weatherreport.util.CityListUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yangzhiwei on 2016/11/8.
 */
public class AutoCompleteAdapter extends BaseAdapter implements Filterable{

    public Context context;
    private List mObjects;
    private final Object mLock = new Object();
    private ArrayList mOriginalValues;

    public AutoCompleteAdapter(Context context){
        this.context = context;
        mObjects = CityListUtil.getChinaCityList(context);
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public String getItem(int position) {
        return (String) mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        View v = LayoutInflater.from(context).inflate(R.layout.fragment_setting_common_search,viewGroup,false); /////?????null or viewgroup
        TextView mTextView = (TextView) v.findViewById(R.id.fragment_setting_common_searchresult);

        mTextView.setText(getItem(position));
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                editor.putString("default_city",getItem(position));
//                Log.i("default_city:",getItem(position));
//
//            }
//        });

        return v;


    }

    @Override
    public Filter getFilter() {
        SearchFilter filter = new SearchFilter();
        return filter;
    }

    private class SearchFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList(mObjects);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList list;
                synchronized (mLock) {
                    list = new ArrayList(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<City> values;
                synchronized (mLock) {
                    values = new ArrayList(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList newValues = new ArrayList();

                for (int i = 0; i < count; i++) {
                    final City value =  values.get(i);
                    final String valueText = value.cityZh;

                    // First match against the whole, non-splitted value
                    if (valueText.contains(prefixString)) {
                        Log.i("equal?"+valueText,"+"+prefixString);
                        newValues.add(value.cityZh);
                    }
                    else {
                        final String[] words = valueText.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].contains(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
                Log.i("newValues.size",String.valueOf(newValues.size()));
            }

            return results;
        }




        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            mObjects = (List) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }


    }



}
