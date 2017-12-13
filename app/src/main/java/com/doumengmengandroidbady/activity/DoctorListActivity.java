package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DoctorListActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private Button bt_search;
    private RelativeLayout rl_doctor , rl_hospital;
    private TextView tv_doctor , tv_hospital;
    private ListView lv_doctor , lv_hospital;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        findView();
        configView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        bt_search = findViewById(R.id.bt_search);
        rl_doctor = findViewById(R.id.rl_doctor);
        rl_hospital = findViewById(R.id.rl_hospital);
        tv_doctor = findViewById(R.id.tv_doctor);
        tv_hospital = findViewById(R.id.tv_hospital);
        lv_doctor = findViewById(R.id.lv_doctor);
        lv_hospital = findViewById(R.id.lv_hospital);
    }

    private void configView(){
        rl_back.setOnClickListener(listener);
        bt_search.setOnClickListener(listener);
        rl_doctor.setOnClickListener(listener);
        rl_hospital.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.bt_search:
                    startActivity(SearchActivity.class);
                    break;
                case R.id.rl_doctor:
                    //TODO
                    break;
                case R.id.rl_hospital:
                    //TODO
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

}
