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

public class PersonCenterActivity extends BaseActivity {

    private TextView tv_title;
    private RelativeLayout rl_back;
    private RelativeLayout rl_child_info , rl_parent_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        findView();
        configView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findView(){
        tv_title = findViewById(R.id.tv_title);
        rl_back = findViewById(R.id.rl_back);
        rl_child_info = findViewById(R.id.rl_child_info);
        rl_parent_info = findViewById(R.id.rl_parent_info);
    }

    private void configView(){
        tv_title.setText(R.string.person_center);

        rl_back.setOnClickListener(listener);
        rl_child_info.setOnClickListener(listener);
        rl_parent_info.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_child_info:
                    break;
                case R.id.rl_parent_info:
                    break;
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

}
