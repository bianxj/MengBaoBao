package com.doumengmeng.doctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.view.GraphModule;

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
        initTitle();
        initPrompt();
        initOverTime();
        initBabyBaseInfo();
        initMeasureInfo();
        initFeedInfo();
        initAssistFootInfo();
        initMessageBoard();
        initPictureView();
        initGraphModule();
        initDevelopment();
        initAssessmentResult();
        initThreeButton();
        initAnslysis();
        initParentingGuide();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        rl_complete.setVisibility(View.VISIBLE);
        tv_title = findViewById(R.id.tv_title);

        tv_title.setText(getString(R.string.assessment));
        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);
    }

    private RelativeLayout rl_prompt,rl_prompt_close;
    private TextView tv_prompt;
    private void initPrompt(){
        rl_prompt = findViewById(R.id.rl_prompt);
        rl_prompt_close = findViewById(R.id.rl_prompt_close);
        tv_prompt = findViewById(R.id.tv_prompt);


    }

    private void initOverTime(){
        tv_over_time = findViewById(R.id.tv_over_time);
    }

    private TextView tv_baby_name,tv_baby_gender,tv_birthday,tv_pregnancy_weeks,
            tv_born_weight,tv_born_height,tv_parity_count,tv_birth_count,tv_mon_age,
            tv_born_type,tv_delivery_methods,tv_assisted_reproductive,tv_birth_injury,
            tv_neonatalasphyxia,tv_intracranial_hemorrhage,tv_hereditary_history,
            tv_allergic_history,tv_past_history;
    private void initBabyBaseInfo(){
        tv_baby_name = findViewById(R.id.tv_baby_name);
        tv_baby_gender = findViewById(R.id.tv_baby_gender);
        tv_birthday = findViewById(R.id.tv_birthday);
        tv_pregnancy_weeks = findViewById(R.id.tv_pregnancy_weeks);
        tv_born_weight = findViewById(R.id.tv_born_weight);
        tv_born_height = findViewById(R.id.tv_born_height);
        tv_parity_count = findViewById(R.id.tv_baby_name);
        tv_birth_count = findViewById(R.id.tv_birth_count);
        tv_mon_age = findViewById(R.id.tv_mon_age);
        tv_born_type = findViewById(R.id.tv_born_type);
        tv_delivery_methods = findViewById(R.id.tv_delivery_methods);
        tv_assisted_reproductive = findViewById(R.id.tv_born_type);
        tv_birth_injury = findViewById(R.id.tv_birth_injury);
        tv_neonatalasphyxia = findViewById(R.id.tv_neonatalasphyxia);
        tv_intracranial_hemorrhage = findViewById(R.id.tv_intracranial_hemorrhage);
        tv_hereditary_history = findViewById(R.id.tv_hereditary_history);
        tv_allergic_history = findViewById(R.id.tv_allergic_history);
        tv_past_history = findViewById(R.id.tv_past_history);
    }

    private TextView tv_current_month,tv_correct_month,tv_height,tv_weight,tv_head,
            tv_chest,tv_night_sleep,tv_day_sleep,tv_cacation,tv_micturition;
    private TextView tv_height_reference_value,tv_weight_reference_value,
            tv_head_reference_value,tv_chest_reference_value,tv_night_sleep_reference_value,
            tv_day_sleep_reference_value;
    private void initMeasureInfo(){
        tv_current_month = findViewById(R.id.tv_current_month);
        tv_correct_month = findViewById(R.id.tv_correct_month);
        tv_height = findViewById(R.id.tv_height);
        tv_weight = findViewById(R.id.tv_weight);
        tv_head = findViewById(R.id.tv_head);
        tv_chest = findViewById(R.id.tv_chest);
        tv_night_sleep = findViewById(R.id.tv_night_sleep);
        tv_day_sleep = findViewById(R.id.tv_day_sleep);
        tv_cacation = findViewById(R.id.tv_cacation);
        tv_micturition = findViewById(R.id.tv_micturition);

        tv_height_reference_value = findViewById(R.id.tv_height_reference_value);
        tv_weight_reference_value = findViewById(R.id.tv_weight_reference_value);
        tv_head_reference_value = findViewById(R.id.tv_head_reference_value);
        tv_chest_reference_value = findViewById(R.id.tv_chest_reference_value);
        tv_night_sleep_reference_value = findViewById(R.id.tv_night_sleep_reference_value);
        tv_day_sleep_reference_value = findViewById(R.id.tv_day_sleep_reference_value);
    }

    private TextView tv_human_feed,tv_human_feed_count,tv_human_feed_all,tv_formula_feed,
            tv_formula_feed_count,tv_formula_feed_all,tv_feed_all;
    private void initFeedInfo(){
        tv_human_feed = findViewById(R.id.tv_human_feed);
        tv_human_feed_count = findViewById(R.id.tv_human_feed_count);
        tv_human_feed_all = findViewById(R.id.tv_human_feed_all);
        tv_formula_feed = findViewById(R.id.tv_formula_feed);
        tv_formula_feed_count = findViewById(R.id.tv_formula_feed_count);
        tv_formula_feed_all = findViewById(R.id.tv_formula_feed_all);
        tv_feed_all = findViewById(R.id.tv_feed_all);
    }

    private TextView tv_milk,tv_milk_powder,tv_rice_flour,tv_food,tv_porridge,tv_rice,
            tv_meat,tv_egg,tv_bean,tv_vegetables,tv_fruit,tv_water,tv_vitamin,tv_calcium,
            tv_other,tv_appetite;
    private void initAssistFootInfo(){
        tv_milk = findViewById(R.id.tv_milk);
        tv_milk_powder = findViewById(R.id.tv_milk_powder);
        tv_rice_flour = findViewById(R.id.tv_rice_flour);
        tv_food = findViewById(R.id.tv_food);
        tv_porridge = findViewById(R.id.tv_porridge);
        tv_rice = findViewById(R.id.tv_rice);
        tv_meat = findViewById(R.id.tv_meat);
        tv_egg = findViewById(R.id.tv_egg);
        tv_bean = findViewById(R.id.tv_bean);
        tv_vegetables = findViewById(R.id.tv_vegetables);
        tv_fruit = findViewById(R.id.tv_fruit);
        tv_water = findViewById(R.id.tv_water);
        tv_vitamin = findViewById(R.id.tv_vitamin);
        tv_calcium = findViewById(R.id.tv_calcium);
        tv_other = findViewById(R.id.tv_other);
        tv_appetite = findViewById(R.id.tv_appetite);
    }

    private TextView tv_message_board;
    private void initMessageBoard(){
        tv_message_board = findViewById(R.id.tv_message_board);
    }

    private GridView gv_upload_report;
    private void initPictureView(){
        gv_upload_report = findViewById(R.id.gv_upload_report);
    }

    private void setGridViewHeight(GridView gridview) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int numColumns= gridview.getNumColumns(); //5
        int totalHeight = 0;
        // 计算每一列的高度之和
        for (int i = 0; i < listAdapter.getCount(); i += numColumns) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }
        // 获取gridview的布局参数
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        params.height = totalHeight;
        gridview.setLayoutParams(params);
    }

    private GraphModule graph_module;
    private void initGraphModule(){
        graph_module = findViewById(R.id.graph_module);
    }

    private TextView tv_developmental_title;
    private LinearLayout ll_developmental_content;
    private void initDevelopment(){
        tv_developmental_title = findViewById(R.id.tv_developmental_title);
        ll_developmental_content = findViewById(R.id.ll_developmental_content);
    }

    private Button bt_hospital_report,bt_history_report,bt_parent_info;
    private void initThreeButton(){
        bt_hospital_report = findViewById(R.id.bt_hospital_report);
        bt_history_report = findViewById(R.id.bt_history_report);
        bt_parent_info = findViewById(R.id.bt_parent_info);

        bt_hospital_report.setOnClickListener(listener);
        bt_history_report.setOnClickListener(listener);
        bt_parent_info.setOnClickListener(listener);
    }

    private TextView tv_weight_content,tv_height_content,tv_bmi_content,tv_other_content;
    private void initAssessmentResult(){
        tv_weight_content = findViewById(R.id.tv_weight_content);
        tv_height_content = findViewById(R.id.tv_height_content);
        tv_bmi_content = findViewById(R.id.tv_bmi_content);
        tv_other_content = findViewById(R.id.tv_other_content);
    }

    private EditText et_report_content_1,et_report_content_2,et_report_content_3,
            et_report_content_4;
    private void initAnslysis(){
        et_report_content_1 = findViewById(R.id.et_report_content_1);
        et_report_content_2 = findViewById(R.id.et_report_content_2);
        et_report_content_3 = findViewById(R.id.et_report_content_3);
        et_report_content_4 = findViewById(R.id.et_report_content_4);
    }

    private Button bt_parenting_guide;
    private LinearLayout ll_parenting_guide;
    private void initParentingGuide(){
        bt_parenting_guide = findViewById(R.id.bt_parenting_guide);
        ll_parenting_guide = findViewById(R.id.ll_parenting_guide);
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
                    submitAssessment();
                    break;
                case R.id.bt_hospital_report:
                    goHospitalReportActivity();
                    break;
                case R.id.bt_history_report:
                    goHistoryReportActivity();
                    break;
                case R.id.bt_parent_info:
                    goParentInfoActivity();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void submitAssessment(){
        //TODO
        if ( checkAssessment() ){

        }
    }

    private boolean checkAssessment(){
        //TODO
        return true;
    }

    private void goHospitalReportActivity(){
        Intent intent = new Intent(this,HospitalReportActivity.class);
        startActivity(intent);
    }

    private void goHistoryReportActivity(){
        Intent intent = new Intent(this,HistoryReportActivity.class);
        startActivity(intent);
    }

    private void goParentInfoActivity(){
        Intent intent = new Intent(this,ParentInfoActivity.class);
        startActivity(intent);
    }

}
