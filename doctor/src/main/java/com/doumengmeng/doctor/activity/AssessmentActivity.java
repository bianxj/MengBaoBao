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

public class AssessmentActivity extends BaseActivity {

    private RelativeLayout rl_back , rl_complete;
    private TextView tv_title;
    private TextView tv_over_time;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        rl_complete.setVisibility(View.VISIBLE);
        tv_title = findViewById(R.id.tv_title);

        tv_over_time = findViewById(R.id.tv_over_time);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_complete:
                    //TODO
                    assessmentComplete();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void assessmentComplete(){
        //TODO
    }

}
