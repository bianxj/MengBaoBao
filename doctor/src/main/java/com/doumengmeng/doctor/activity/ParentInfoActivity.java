package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.response.AssessmentDetailResponse;
import com.doumengmeng.doctor.util.GsonUtil;

/**
 * Created by Administrator on 2018/3/1.
 */

public class ParentInfoActivity extends BaseActivity {

    public final static String IN_PARAM_PARENT_INFO = "in_parent_info";

    private RelativeLayout rl_back;
    private TextView tv_title;

    private TextView tv_father_name,tv_father_culture,tv_father_height,tv_father_weight,tv_father_BMI;
    private TextView tv_mother_name,tv_mother_culture,tv_mother_height,tv_mother_weight,tv_mother_BMI;
    private AssessmentDetailResponse.ParentData parentData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        findView();
    }

    private void findView(){
        loadParentData();
        initTitle();
        initFatherInfo();
        initMotherInfo();
    }

    private void loadParentData(){
        parentData = GsonUtil.getInstance().fromJson(getIntent().getStringExtra(IN_PARAM_PARENT_INFO), AssessmentDetailResponse.ParentData.class);
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
        tv_father_name.setText(parentData.getDadname());
        tv_father_culture.setText(parentData.getDadeducation());
        tv_father_height.setText(String.format(getString(R.string.format_cm),parentData.getDadheight()));
        tv_father_weight.setText(String.format(getString(R.string.format_kg),parentData.getDadweight()));
        tv_father_BMI.setText(String.format(getString(R.string.format_kg_divide_m_square),parentData.getDadBmi()));
    }

    private void initMotherInfo(){
        tv_mother_name = findViewById(R.id.tv_mother_name);
        tv_mother_culture = findViewById(R.id.tv_mother_culture);
        tv_mother_height = findViewById(R.id.tv_mother_height);
        tv_mother_weight = findViewById(R.id.tv_mother_weight);
        tv_mother_BMI = findViewById(R.id.tv_mother_BMI);

        //TODO
        tv_mother_name.setText(parentData.getMumname());
        tv_mother_culture.setText(parentData.getMumeducation());
        tv_mother_height.setText(String.format(getString(R.string.format_cm),parentData.getMumheight()));
        tv_mother_weight.setText(String.format(getString(R.string.format_kg),parentData.getMumweight()));
        tv_mother_BMI.setText(String.format(getString(R.string.format_kg_divide_m_square),parentData.getMumbmi()));
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
