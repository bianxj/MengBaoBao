package com.doumengmeng.doctor.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.response.entity.Feature;
import com.doumengmeng.doctor.response.entity.HospitalReport;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.view.GraphModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/3/1.
 */

public class HospitalReportDetailActivity extends BaseActivity {

    public final static String IN_PARAM_REPORT_DATA = "in_report_data";

    private RelativeLayout rl_back;
    private TextView tv_title;

    //基本信息
    private TextView tv_hospital_name ,
            tv_department , tv_child_code ,
            tv_name,tv_gender,
            tv_birthday,tv_current_month,
            tv_corrent_month;
    private LinearLayout ll_corrent_month;

    //测量信息
    private TextView tv_height,tv_weight,
            tv_head,tv_chest,
            tv_BMI;

    private TextView tv_feeding_type,tv_develop_history,
            tv_disease_history,tv_current_history;

    //曲线图

//    //标尺
//    private ScaleplateView sv_weight,sv_height,sv_height_weight,sv_BMI;

    //发育行为
    private TextView tv_develop_behavior;
    private LinearLayout ll_develop_behavior;

    //评价结果
    private TextView tv_result_weight,tv_result_height,tv_result_height_weight,tv_result_other;
    //医生建议
    private TextView tv_suggest;
    //表单底部
    private TextView tv_doctor_name,tv_report_name,tv_report_time;

    private HospitalReport report;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_report_detail);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        tv_hospital_name = findViewById(R.id.tv_hospital_name);
        tv_department = findViewById(R.id.tv_department);
        tv_child_code = findViewById(R.id.tv_child_code);
        tv_name = findViewById(R.id.tv_name);
        tv_gender = findViewById(R.id.tv_gender);
        tv_birthday = findViewById(R.id.tv_birthday);
        tv_current_month = findViewById(R.id.tv_current_month);
        tv_corrent_month = findViewById(R.id.tv_corrent_month);

        ll_corrent_month = findViewById(R.id.ll_corrent_month);

        tv_height = findViewById(R.id.tv_height);
        tv_weight = findViewById(R.id.tv_weight);
        tv_head = findViewById(R.id.tv_head);
        tv_chest = findViewById(R.id.tv_chest);
        tv_BMI = findViewById(R.id.tv_BMI);

        tv_feeding_type = findViewById(R.id.tv_feeding_type);
        tv_develop_history = findViewById(R.id.tv_develop_history);
        tv_disease_history = findViewById(R.id.tv_disease_history);
        tv_current_history = findViewById(R.id.tv_current_history);

//        findDiagramView();
//        sv_weight = findViewById(R.id.sv_weight);
//        sv_height = findViewById(R.id.sv_height);
//        sv_height_weight = findViewById(R.id.sv_height_weight);
//        sv_BMI = findViewById(R.id.sv_BMI);

        tv_develop_behavior = findViewById(R.id.tv_develop_behavior);
        ll_develop_behavior = findViewById(R.id.ll_develop_behavior);

        tv_result_weight = findViewById(R.id.tv_result_weight);
        tv_result_height = findViewById(R.id.tv_result_height);
        tv_result_height_weight = findViewById(R.id.tv_result_height_weight);
        tv_result_other = findViewById(R.id.tv_result_other);

        tv_suggest = findViewById(R.id.tv_suggest);

        tv_doctor_name = findViewById(R.id.tv_doctor_name);
        tv_report_name = findViewById(R.id.tv_report_name);
        tv_report_time = findViewById(R.id.tv_report_time);
        getReport();
    }

    private void getReport(){
        report = GsonUtil.getInstance().fromJson(getIntent().getStringExtra(IN_PARAM_REPORT_DATA),HospitalReport.class);
        if ( report != null ) {
            initView();
        }
    }

    private void initView(){
        tv_title.setText(R.string.hospital_report);
        rl_back.setOnClickListener(listener);

        tv_hospital_name.setText(report.getHospitalName());
        tv_department.setText(String.format(getString(R.string.hospital_report_department),report.getDepartmentName()));
        tv_child_code.setText(String.format(getString(R.string.hospital_report_child_code),report.getFileCode()));
        tv_name.setText(String.format(getString(R.string.hospital_report_name),report.getTrueName()));
        tv_gender.setText(String.format(getString(R.string.hospital_report_gender),report.getGender()));
        tv_birthday.setText(String.format(getString(R.string.hospital_report_birthday),report.getBirthday()));

        tv_current_month.setText(String.format(getString(R.string.hospital_report_current_month),report.getCurrentMonthAgeString()));
        if ( report.getCurrentMonthAgeString().equals(report.getCorrectMonthAgeString()) ){
            ll_corrent_month.setVisibility(View.GONE);
        } else {
            ll_corrent_month.setVisibility(View.VISIBLE);
            tv_corrent_month.setText(String.format(getString(R.string.hospital_report_corrent_month),report.getCorrectMonthAgeString()));
        }

        tv_height.setText(String.format(getString(R.string.hospital_report_height),report.getHeight()));
        tv_weight.setText(String.format(getString(R.string.hospital_report_weight),report.getWeight()));
        tv_head.setText(String.format(getString(R.string.hospital_report_head),report.getHeadCircumference()));
        tv_chest.setText(String.format(getString(R.string.hospital_report_chest),report.getChestCircumference()));
        tv_BMI.setText(String.format(getString(R.string.hospital_report_bmi),report.getBmi()));

        tv_feeding_type.setText(report.getFoodType());
        tv_current_history.setText(report.getPresentIllnessHistory());

        tv_result_weight.setText(String.format(getString(R.string.hospital_report_weight_result),report.getWeightEvaluation()));
        tv_result_height.setText(String.format(getString(R.string.hospital_report_height_result),report.getHeightEvaluation()));
        tv_result_height_weight.setText(String.format(getString(R.string.hospital_report_hw_result),report.getHwEvaluation()));
        tv_result_other.setText(String.format(getString(R.string.hospital_report_other_result),report.getFeatureEvaluation()));

        tv_suggest.setText(report.getDoctorAdvice());

        tv_doctor_name.setText(String.format(getString(R.string.hospital_report_doctor_name),report.getDoctorName()));
        tv_report_name.setText(String.format(getString(R.string.hospital_report_reporter_name),report.getCureDoctor()));
        tv_report_time.setText(String.format(getString(R.string.hospital_report_time),report.getRecordDay()));

        initDevelopBehavior();
        initGraphView();
    }

    private void initDevelopBehavior(){
        tv_develop_behavior.setText(String.format(getString(R.string.hospital_report_develop),report.getFeatureAge()));
        initDevelopment(generateDevelopmentData(report.getFeatureAge(),report.getRecordTime(),report.getFeatures()));
    }

    private void initGraphView(){
        GraphModule graph_module = findViewById(R.id.graph_module);
        graph_module.setData(report.getImageData(),Integer.parseInt(report.getMonthAge()),report.isMale(),false);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    //------------------------------------发育行为--------------------------------------------------

    private Map<String,List<DevelopmentalItem>> generateDevelopmentData(String correntMonth , String recordTime, List<String> selected){
        Map<String,List<DevelopmentalItem>> map = new HashMap<>();

        List<String> ages = new ArrayList<>();
        ages.add(correntMonth);
        List<Feature> features = DaoManager.getInstance().getFeatureDao().searchFeatureList(this,ages,recordTime);

        for (Feature feature:features){
            List<DevelopmentalItem> developmentalItems;
            if ( !map.containsKey(feature.getFeaturetype()) ){
                developmentalItems = new ArrayList<>();
                map.put(feature.getFeaturetype(),developmentalItems);
            } else {
                developmentalItems = map.get(feature.getFeaturetype());
            }
            DevelopmentalItem item = new DevelopmentalItem();
            item.setValue(feature.getDetaildesc());
            if ( selected != null ) {
                item.setCheck(selected.contains(feature.getId()));
            } else {
                item.setCheck(false);
            }
            item.setMark(!"0".equals(feature.getPointtag()));
            developmentalItems.add(item);
        }
        return map;
    }

    //初始化发行为
    private void initDevelopment(Map<String,List<DevelopmentalItem>> maps){
        Set<String> keys = maps.keySet();
        for (String key:keys){
            ll_develop_behavior.addView(createSubItem(key,maps.get(key)),0);
        }
    }

    /**
     * 作者: 边贤君
     * 描述: 创建发育行为条目
     * 日期: 2018/1/18 9:44
     */
    private View createSubItem(String title , List<DevelopmentalItem> contents){
        RelativeLayout layout = new RelativeLayout(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);

        View divider = new View(this);
        divider.setBackgroundColor(getResources().getColor(R.color.linePink));
        divider.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelOffset(R.dimen.y1px)));
        layout.addView(divider);

        TextView tv_title = new TextView(this);
        tv_title.setText(title);
        tv_title.setTextColor(getResources().getColor(R.color.second_black));
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y24px));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y35px);
        params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.y20px);
        tv_title.setLayoutParams(params);
        layout.addView(tv_title);

        View right = createCheckLine(contents);
        layout.addView(right);

        return layout;
    }

    /**
     * 作者: 边贤君
     * 描述: 创建发育行为条目中的选项
     * 日期: 2018/1/18 9:44
     */
    private View createCheckLine(List<DevelopmentalItem> contents){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.y24px);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP|RelativeLayout.ALIGN_PARENT_LEFT);
        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y35px);
        params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x159px);
        params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.x20px);
        layout.setLayoutParams(params);

        for (DevelopmentalItem content:contents){
            LinearLayout subLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.y10px);
            subLayout.setOrientation(LinearLayout.HORIZONTAL);
            subLayout.setLayoutParams(layoutParams);

            CheckBox checkBox = new CheckBox(this);
            checkBox.setButtonDrawable(null);
            checkBox.setBackgroundResource(R.drawable.cb_hospital_report);
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x34px),getResources().getDimensionPixelOffset(R.dimen.x34px)));
            checkBox.setChecked(content.isCheck());
            subLayout.addView(checkBox);

            TextView tv_content = new TextView(this);
            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            textParam.leftMargin = getResources().getDimensionPixelOffset(R.dimen.y10px);
            textParam.topMargin = -1*getResources().getDimensionPixelOffset(R.dimen.y5px);
            tv_content.setLayoutParams(textParam);
            tv_content.setText(content.getValue());
            tv_content.setTextColor(getResources().getColor(R.color.fourth_gray));
            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y24px));
            subLayout.addView(tv_content);

            if ( content.isMark ){
                TextView tv_mark = new TextView(this);
                LinearLayout.LayoutParams markParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                textParam.topMargin = -1*getResources().getDimensionPixelOffset(R.dimen.y5px);
                tv_content.setLayoutParams(markParam);
                tv_content.setText("*");
                tv_content.setTextColor(Color.RED);
                tv_content.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y24px));
                subLayout.addView(tv_mark);
            }

            layout.addView(subLayout);
        }

        return layout;
    }

    public static class DevelopmentalItem{
        private String value;
        private boolean isCheck;
        private boolean isMark;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public boolean isMark() {
            return isMark;
        }

        public void setMark(boolean mark) {
            isMark = mark;
        }
    }

    private void back(){
        finish();
    }

}
