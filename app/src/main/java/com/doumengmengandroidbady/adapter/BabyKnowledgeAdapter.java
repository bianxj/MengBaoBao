package com.doumengmengandroidbady.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doumengmengandroidbady.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class BabyKnowledgeAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> infos;

    public BabyKnowledgeAdapter(Context context,List<String> infos) {
        this.context = context;
        this.infos = infos;
    }

    @Override
    public int getCount() {
        if ( null == infos ){
            return 0;
        }
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        if ( null == infos ){
            return null;
        }
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if ( null == convertView ){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_baby_knowledge,null);
        }
        TextView tv_baby_month = convertView.findViewById(R.id.tv_baby_month);
        tv_baby_month.setText(infos.get(position));
        return convertView;
    }
}
