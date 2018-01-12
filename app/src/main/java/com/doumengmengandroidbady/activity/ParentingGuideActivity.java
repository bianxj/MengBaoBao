package com.doumengmengandroidbady.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;


public class ParentingGuideActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;

    private TabLayout tab;
    private View v_left , v_right;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tab.setSmoothScrollingEnabled(true);
            tab.setOnScrollChangeListener(scrollChangeListener);
        }

        v_left = findViewById(R.id.v_left);
        v_right = findViewById(R.id.v_right);
    }

    private void initData(){
        tv_title.setText("养育指导");

        for (int i=0 ; i<9;i++){
            tab.addTab(tab.newTab().setText("Test"+i));
        }
        tab.addTab(tab.newTab().setText("OTHEREEEEEE"));
    }

    private View.OnScrollChangeListener scrollChangeListener = new View.OnScrollChangeListener() {
        @Override
        public void onScrollChange(View view, int x, int y, int oldX, int oldY) {
            if ( tab.getScrollX() == 0 ){
                v_left.setVisibility(View.GONE);
            } else {
                v_left.setVisibility(View.VISIBLE);
            }
            if ( tab.getScrollX() + tab.getWidth() == tab.getChildAt(0).getMeasuredWidth() ){
                v_right.setVisibility(View.GONE);
            } else {
                v_right.setVisibility(View.VISIBLE);
            }
        }
    };

}
