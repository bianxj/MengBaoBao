package com.doumengmeng.customer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseSwipeActivity;

/**
 * Created by Administrator on 2017/12/8.
 */
public class ContactActivity extends BaseSwipeActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        findView();
        initView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
    }

    private void initView(){
        tv_title.setText(R.string.contact);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( v.getId() == R.id.rl_back ){
                    back();
                }
            }
        });
    }

    protected void back(){
        finish();
    }

}
