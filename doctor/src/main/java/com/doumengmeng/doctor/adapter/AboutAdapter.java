package com.doumengmeng.doctor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doumengmeng.doctor.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class AboutAdapter extends BaseAdapter {

    private List<AboutItem> items;

    public AboutAdapter(List<AboutItem> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if ( view == null ) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_about, null);
        }

        ImageView iv_icon = view.findViewById(R.id.iv_icon);
        TextView tv_title = view.findViewById(R.id.tv_title);

        AboutItem item = items.get(position);
        iv_icon.setImageResource(item.getDrawable());
        tv_title.setText(item.getTitle());
        return view;
    }

    public static class AboutItem{
        private int drawable;
        private String title;

        public AboutItem(int drawable, String title) {
            this.drawable = drawable;
            this.title = title;
        }

        public int getDrawable() {
            return drawable;
        }

        public void setDrawable(int drawable) {
            this.drawable = drawable;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
