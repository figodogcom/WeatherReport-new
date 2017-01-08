package com.yzw.weather.weatherreport.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.model.LeftMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzhiwei on 2016/12/14.
 */
public class DownLeftMenuAdapter extends RecyclerView.Adapter<DownLeftMenuAdapter.ViewHolder> {
    private int mCurrentPosition = -1;
    private DownLeftMenuAdapterCallback mCallback;
    private List<LeftMenuItem> mMenuItemList;

    public DownLeftMenuAdapter() {
        mMenuItemList = new ArrayList<>();
        mMenuItemList.add(new LeftMenuItem("登陆", R.drawable.ic_account_circle_white_24dp));
        mMenuItemList.add(new LeftMenuItem("设置", R.drawable.ic_settings_white_24dp));


    }

    public DownLeftMenuAdapter(DownLeftMenuAdapterCallback callback) {
        this();
        this.mCallback = callback;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        public final ImageView imageView;
        public final ImageView colorImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.leftmenu_textview);
            imageView = (ImageView) itemView.findViewById(R.id.leftmenu_imageview);
            colorImageView = (ImageView) itemView.findViewById(R.id.leftmenu_color);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_leftmenu_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        LeftMenuItem leftMenuItem = mMenuItemList.get(position);

        holder.textView.setText(leftMenuItem.name);
        holder.imageView.setImageResource(leftMenuItem.resId);


        if (mCurrentPosition == position) {
            holder.colorImageView.setBackgroundColor(Color.parseColor("#6d9eeb"));
        } else {
            holder.colorImageView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mCurrentPosition = position;
                notifyDataSetChanged();

                if (mCallback != null) {
                    mCallback.onDownLeftMenuAdapterItemSelected(position);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mMenuItemList.size();
    }

    public interface DownLeftMenuAdapterCallback {
        void onDownLeftMenuAdapterItemSelected(int position);

    }

    public void setCurrentPosition(int position) {
        mCurrentPosition = position;
        notifyDataSetChanged();
    }
}
