package com.doumengmengandroidbady.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/5.
 */
//TODO
public class LoadingActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_loading_percent;
    private ImageView iv_loading_icon;
    private AnimationDrawable drawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        findView();
        configView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( drawable != null ){
            drawable.stop();
        }
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_loading_percent = findViewById(R.id.tv_loading_percent);
        iv_loading_icon = findViewById(R.id.iv_loading_icon);
    }

    private void configView(){
//        AnimationDrawable drawable = (AnimationDrawable) iv_loading_icon.getDrawable();
//        drawable.start();
        RotateAnimation set = (RotateAnimation) AnimationUtils.loadAnimation(LoadingActivity.this,R.anim.loading_rotate);
        set.setDuration(1500);
        set.setRepeatMode(Animation.RESTART);
        set.setRepeatCount(Animation.INFINITE);
        set.setInterpolator(new LinearInterpolator());
        iv_loading_icon.startAnimation(set);
    }

}
