package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/8.
 */

public class SettingActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private RelativeLayout rl_change_pwd , rl_agreement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        findView();
        configView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        rl_change_pwd = findViewById(R.id.rl_change_pwd);
        rl_agreement = findViewById(R.id.rl_agreement);
    }

    private void configView(){
        tv_title.setText(R.string.setting);
        rl_back.setOnClickListener(listener);
        rl_change_pwd.setOnClickListener(listener);
        rl_agreement.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_change_pwd:
                    break;
                case R.id.rl_agreement:
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

}
