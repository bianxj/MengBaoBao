package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.HospitalReportAdapter;
import com.doumengmeng.doctor.base.BaseActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by Administrator on 2018/3/1.
 */

public class HospitalReportActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private XRecyclerView xrv;

    private HospitalReportAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        rl_back.setOnClickListener(listener);

        xrv = findViewById(R.id.xrv);
        initView();
    }

    private void initView(){

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
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
