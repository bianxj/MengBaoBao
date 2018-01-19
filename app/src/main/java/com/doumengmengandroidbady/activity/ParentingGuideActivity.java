package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;


public class ParentingGuideActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;

    private TabLayout tab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parenting_guide);
        findView();
        initData();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        tab = findViewById(R.id.tab);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initData(){
        tv_title.setText("养育指导");

        for (int i=0 ; i<9;i++){
            tab.addTab(tab.newTab().setText("Test"+i));
        }
        tab.addTab(tab.newTab().setText("OTHEREEEEEE"));
    }

}
