package com.doumengmeng.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.entity.SideMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SideMenuAdapter extends BaseAdapter {

    private final List<SideMenuItem> items;
    private final Context context;

    public SideMenuAdapter(Context context,List<SideMenuItem> items) {
        this.context = context;
        if ( null == items ){
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if ( null == convertView ){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_side_menu,null);
        }
        SideMenuItem item = items.get(position);
        ImageView iv = convertView.findViewById(R.id.iv_icon);
        TextView tv = convertView.findViewById(R.id.tv_content);

        iv.setImageResource(item.getDrawable());
        tv.setText(item.getContent());
        return convertView;
    }

}
