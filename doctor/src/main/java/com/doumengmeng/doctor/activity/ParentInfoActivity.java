package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;

/**
 * Created by Administrator on 2018/3/1.
 */

public class ParentInfoActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;

    private TextView tv_father_name,tv_father_culture,tv_father_height,tv_father_weight,tv_father_BMI;
    private TextView tv_mother_name,tv_mother_culture,tv_mother_height,tv_mother_weight,tv_mother_BMI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        findView();
    }

    private void findView(){
        initTitle();
        initFatherInfo();
        initMotherInfo();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        rl_back.setOnClickListener(listener);
        tv_title.setText(R.string.parent_info);
    }

    private void initFatherInfo(){
        tv_father_name = findViewById(R.id.tv_father_name);
        tv_father_culture = findViewById(R.id.tv_father_culture);
        tv_father_height = findViewById(R.id.tv_father_height);
        tv_father_weight = findViewById(R.id.tv_father_weight);
        tv_father_BMI = findViewById(R.id.tv_father_BMI);

        //TODO
    }

    private void initMotherInfo(){
        tv_mother_name = findViewById(R.id.tv_mother_name);
        tv_mother_culture = findViewById(R.id.tv_mother_culture);
        tv_mother_height = findViewById(R.id.tv_mother_height);
        tv_mother_weight = findViewById(R.id.tv_mother_weight);
        tv_mother_BMI = findViewById(R.id.tv_mother_BMI);

        //TODO
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
