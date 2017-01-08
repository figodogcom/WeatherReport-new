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
public class UpLeftMenuAdapter extends RecyclerView.Adapter<UpLeftMenuAdapter.ViewHolder> {


    private int mCurrentPosition = -1;
    private UpLeftMenuAdapterCallback mCallback;

    private List<LeftMenuItem> mMenuItemList;

    public UpLeftMenuAdapter() {
        mMenuItemList = new ArrayList<>();
        mMenuItemList.add(new LeftMenuItem("预报", R.drawable.ic_home_white_24dp));
        mMenuItemList.add(new LeftMenuItem("收藏夹", R.drawable.ic_folder_special_white_24dp));
        mMenuItemList.add(new LeftMenuItem("VIP充值", R.drawable.ic_attach_money_white_24dp));
        mMenuItemList.add(new LeftMenuItem("发送反馈", R.drawable.ic_email_white_24dp));
    }

    public UpLeftMenuAdapter(UpLeftMenuAdapterCallback callback) {
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

        public void bindData(LeftMenuItem leftMenuItem, boolean isCurrent) {
            textView.setText(leftMenuItem.name);
            imageView.setImageResource(leftMenuItem.resId);

            if (isCurrent) {
                colorImageView.setBackgroundColor(Color.parseColor("#6d9eeb"));
            } else {
                colorImageView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            }

//        int color = Color.parseColor(mCurrentPosition == position ? "#6d9eeb" : "#FFFFFF");
//        mImageViewcolor.setBackgroundColor(color);
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

        holder.bindData(leftMenuItem, mCurrentPosition == position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentPosition = position;
                notifyDataSetChanged();

                if (mCallback != null) {
                    mCallback.onUpLeftMenuAdapterItemSelected(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenuItemList.size();
    }

    public interface UpLeftMenuAdapterCallback {
        void onUpLeftMenuAdapterItemSelected(int position);

    }

    public void setUpLeftMenuAdapterCallback(UpLeftMenuAdapterCallback callback) {
        this.mCallback = callback;
    }

    public void setCurrentPosition(int position) {
        mCurrentPosition = position;
        notifyDataSetChanged();
    }
}
