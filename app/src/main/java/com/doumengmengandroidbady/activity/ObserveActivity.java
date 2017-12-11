package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/11.
 */

public class ObserveActivity extends BaseActivity {

    private Button bt_back;
    private TextView tv_title;
    private ImageView iv_ad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observe);
        findView();
        configView();
    }

    private void findView(){
        bt_back = findViewById(R.id.bt_back);
        tv_title = findViewById(R.id.tv_title);
        iv_ad = findViewById(R.id.iv_ad);
    }

    private void configView() {
        bt_back.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bt_back:
                    back();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

}
