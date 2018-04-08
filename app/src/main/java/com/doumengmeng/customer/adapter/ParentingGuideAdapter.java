package com.doumengmeng.customer.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.response.ParentingGuidanceResponse;

import java.util.List;


public class ParentingGuideAdapter extends BaseAdapter {

    private final List<ParentingGuidanceResponse.Result.ParentingGuideItem> items;

    public ParentingGuideAdapter(List<ParentingGuidanceResponse.Result.ParentingGuideItem> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_parenting_guidance,null);
        }

        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);

        ParentingGuidanceResponse.Result.ParentingGuideItem item = items.get(i);
        if (TextUtils.isEmpty(item.getCustomnurturetitle())){
            tv_title.setText(item.getNurturetitle());
            tv_content.setText(item.getNurturedesc());
        } else {
            tv_title.setText(item.getCustomnurturetitle());
            tv_content.setText(item.getCustomnurturedesc());
        }
        return view;
    }

}
