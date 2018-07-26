package com.doumengmeng.doctor.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseSwipeActivity;
import com.doumengmeng.doctor.util.AppUtil;

/**
 * Created by Administrator on 2018/3/1.
 */

public class AboutActivity extends BaseSwipeActivity {
    private RelativeLayout rl_back;
    private TextView tv_title;

    private TextView tv_version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findView();
        initTitle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findView(){
        initTitle();
        initVersion();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        tv_title.setText(R.string.about);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    private void initVersion(){
        tv_version = findViewById(R.id.tv_version);

        try {
            tv_version.setText("医生版:"+ AppUtil.getVersionName(this));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void back(){
        finish();
    }
}
