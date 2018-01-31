package com.doumengmengandroidbady.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.ParentingGuideAdapter;
import com.doumengmengandroidbady.base.BaseFragment;
import com.doumengmengandroidbady.response.ParentingGuidanceData;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ParentingGuideFragment extends BaseFragment {

    private ListView lv;
    private List<ParentingGuidanceData.ParentingGuideItem> items;
    private ParentingGuideAdapter adapter;

    public void setParentingGuideItem(List<ParentingGuidanceData.ParentingGuideItem> items) {
        this.items = items;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parenting_guidance, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        lv = view.findViewById(R.id.lv);
        adapter = new ParentingGuideAdapter(items);
        lv.setAdapter(adapter);
        adapter.notifyDataSetInvalidated();
    }

}
