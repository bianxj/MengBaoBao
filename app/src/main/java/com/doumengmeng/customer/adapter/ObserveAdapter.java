package com.doumengmeng.customer.adapter;

import android.content.Context;
import android.support.v4.widget.Space;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doumengmeng.customer.R;

import java.util.List;

public class ObserveAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> contents;

    public ObserveAdapter(Context context,List<String> contents) {
        this.contents = contents;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if ( view == null ){
            view = LayoutInflater.from(context).inflate(R.layout.item_observe,null);
        }
        TextView tv_content = view.findViewById(R.id.tv_content);
        Space space = view.findViewById(R.id.space);
        if ( i == contents.size()-1 ){
            space.setVisibility(View.VISIBLE);
        } else {
            space.setVisibility(View.GONE);
        }
        String content = contents.get(i);
        if ( content.contains("“购买”") ){
            tv_content.setText(Html.fromHtml(content.replace("“购买”","<b>“购买”</b>")));
        } else {
            tv_content.setText(contents.get(i));
        }
        return view;
    }
}
