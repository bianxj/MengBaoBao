package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/8.
 */
public class ContactActivity extends BaseActivity {

    private Button bt_back;
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        findView();
        configView();
    }

    private void findView(){
        bt_back = findViewById(R.id.bt_back);
        tv_title = findViewById(R.id.tv_title);
    }

    private void configView(){
        tv_title.setText(R.string.contact);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( v.getId() == R.id.bt_back ){
                    back();
                }
            }
        });
    }

    private void back(){
        finish();
    }

}
