package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/5.
 */
//TODO
public class GuideActivity extends BaseActivity {

    private ViewPager vp_guide;
    private LinearLayout ll_guide_dot;
    private Button bt_guide_register , bt_guide_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findView();
        configView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findView(){
        vp_guide = findViewById(R.id.vp_guide);
        ll_guide_dot = findViewById(R.id.ll_guide_dot);
        bt_guide_register = findViewById(R.id.bt_guide_register);
        bt_guide_login = findViewById(R.id.bt_guide_login);
    }

    private void configView(){
        bt_guide_register.setOnClickListener(listener);
        bt_guide_login.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bt_guide_register:
                    startActivity(RegisterActivity.class);
                    break;
                case R.id.bt_guide_login:
                    startActivity(LoginActivity.class);
                    break;
            }
        }
    };

}
