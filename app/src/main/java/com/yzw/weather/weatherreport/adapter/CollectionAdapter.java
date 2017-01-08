package com.yzw.weather.weatherreport.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzw.weather.weatherreport.R;
import com.yzw.weather.weatherreport.SearchActivity;
import com.yzw.weather.weatherreport.db.Favourite;
import com.yzw.weather.weatherreport.db.Start;
import com.yzw.weather.weatherreport.view.CollectionItemView;

import java.util.List;

/**
 * Created by yangzhiwei on 2016/12/16.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.BaseViewHolder> {
    private static final String TAG = CollectionAdapter.class.getCanonicalName();

    private static final int VIEW_TYPE_COLLECTION = 1;
    private static final int VIEW_TYPE_ADD = 2;
    private static final int VIEW_TYPE_TEXT = 3;

    private CollectionAdapterCallback callback;

    private Start start;
    private List<Favourite> favouriteList;

    public CollectionAdapter(CollectionAdapterCallback callback, Start start, List<Favourite> favouriteList) {
        this.callback = callback;
        this.start = start;
        this.favouriteList = favouriteList;
    }


    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ViewHolderAdd extends BaseViewHolder {

        public ViewHolderAdd(View itemView) {
            super(itemView);

        }
    }

    public class ViewHolderText extends BaseViewHolder {
        public final TextView textview_text;

        public ViewHolderText(View itemView) {
            super(itemView);
            textview_text = (TextView) itemView.findViewById(R.id.collection_text);
        }
    }

    public class ViewHolderCollection extends BaseViewHolder {

        public ViewHolderCollection(View v) {
            super(v);
        }

    }


    @Override
    public int getItemViewType(int position) {
//        if(position == cursor.getCount()){
//            return VIEW_TYPE_ADD;
//        }else return VIEW_TYPE_COLLECTION;
        if (position == 0 || position == 2)
            return VIEW_TYPE_TEXT;
        else if (position == 1)
            return VIEW_TYPE_COLLECTION;
        else if (position == 3 + favouriteList.size())
            return VIEW_TYPE_ADD;
        else return VIEW_TYPE_COLLECTION;


//        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return favouriteList.size() + 4;
    }


    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_COLLECTION) {
            CollectionItemView view = new CollectionItemView(viewGroup.getContext());
            return new ViewHolderCollection(view);
        } else if (viewType == VIEW_TYPE_ADD) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.fragment_collection_addview, viewGroup, false);
            return new ViewHolderAdd(v);
        } else if (viewType == VIEW_TYPE_TEXT){
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.fragment_collection_text, viewGroup, false);
            return new ViewHolderText(v);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, final int position) {

        Log.i(TAG, "position: " + position);

        if (position == 0) {
            ViewHolderText viewHolderText = (ViewHolderText) viewHolder;
            viewHolderText.textview_text.setText("启动位置");

        } else if (position == 1) {

            ViewHolderCollection viewHolder_collection = (ViewHolderCollection) viewHolder;

            ((CollectionItemView) viewHolder_collection.itemView).bindStart(start);

            viewHolder_collection.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onCollectionAdapterClickCity(start.getCityid());
                }
            });

        } else if (position == 2) {

            ViewHolderText viewHolderText = (ViewHolderText) viewHolder;
            viewHolderText.textview_text.setText("最喜欢的地方");

        } else if (position == 3 + favouriteList.size()) {

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SearchActivity.class);
                    context.startActivity(intent);
                }
            });
        } else {

            int realPosition = position - 3;

            final Favourite favourite = favouriteList.get(realPosition);

            ViewHolderCollection viewHolder_collection = (ViewHolderCollection) viewHolder;

            ((CollectionItemView) viewHolder_collection.itemView).bindFavourite(favourite);

            viewHolder_collection.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onCollectionAdapterClickCity(favourite.getCityid());
                    }
                }
            });

            viewHolder_collection.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.i("ZZZ", "ZZZ");
                    showPopupWindow(v, favourite);

                    return true;
                }
            });

        }
    }

    private void showPopupWindow(View view, final Favourite favourite) {

        final Context context = view.getContext();

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.pop_window, null);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 设置按钮的点击事件
        TextView textView_pop = (TextView) contentView.findViewById(R.id.textview_popwindow);
        textView_pop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onCollectionAdapterDeleteCity(favourite);
                }

                popupWindow.dismiss();
            }
        });


        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.write));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
    }

    public interface CollectionAdapterCallback {
        void onCollectionAdapterClickCity(String cityid);
        void onCollectionAdapterDeleteCity(Favourite favourite);
    }
}
