package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.view.CircleImageView;
import com.doumengmengandroidbady.view.DiagramView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者: 边贤君
 * 描述: 评估页面
 * 创建日期: 2018/1/11 9:21
 */
public class AssessmentActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;

    private CircleImageView civ_doctor;
    private TextView tv_doctor_name,tv_doctor_hospital,tv_doctor_position;
    private TextView tv_baby_name,tv_baby_gender,tv_baby_birthday,tv_realy_age,tv_correct_age,
            tv_height,tv_weight,tv_BMI;

    //发育行为
    private LinearLayout ll_developmental_behavior;
    private LinearLayout ll_developmental_content;
    private TextView tv_developmental_title;

    //评估结果
    private LinearLayout ll_assessment_result;
    private TextView tv_weight_content, tv_height_content,tv_bmi_content,tv_other_content;

    //报告解读
    private LinearLayout ll_report_interpretation;
    private TextView tv_report_content_1 , tv_report_content_2 ,tv_report_content_3 ,tv_report_content_4;

    //提示信息
    private RelativeLayout rl_prompt_message;
    private TextView tv_prompt_message;

    //养育指导
    private RelativeLayout rl_parenting_guide;
    private ImageView iv_parenting_guide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        findView();
        initData();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        //医生信息
        civ_doctor = findViewById(R.id.civ_doctor);
        tv_doctor_name = findViewById(R.id.tv_doctor_name);
        tv_doctor_hospital = findViewById(R.id.tv_doctor_hospital);
        tv_doctor_position = findViewById(R.id.tv_doctor_position);

        //基本信息
        tv_baby_name = findViewById(R.id.tv_baby_name);
        tv_baby_gender = findViewById(R.id.tv_baby_gender);
        tv_baby_birthday = findViewById(R.id.tv_baby_birthday);
        tv_realy_age = findViewById(R.id.tv_realy_age);
        tv_correct_age = findViewById(R.id.tv_correct_age);
        tv_height = findViewById(R.id.tv_height);
        tv_weight = findViewById(R.id.tv_weight);
        tv_BMI = findViewById(R.id.tv_BMI);

        //图标信息
        iv_details = findViewById(R.id.iv_details);
        diagram_view = findViewById(R.id.diagram_view);
        rl_data = findViewById(R.id.rl_data);
        bt_details = findViewById(R.id.bt_details);

        rg_weight = findViewById(R.id.rg_weight);
        rg_height = findViewById(R.id.rg_height);
        rg_BMI = findViewById(R.id.rg_BMI);

        diagramButtons.add(rg_weight);
        diagramButtons.add(rg_height);
        diagramButtons.add(rg_BMI);

        //发育行为
        ll_developmental_behavior = findViewById(R.id.ll_developmental_behavior);
        ll_developmental_content = findViewById(R.id.ll_developmental_content);
        tv_developmental_title = findViewById(R.id.tv_developmental_title);

        //评估结果
        ll_assessment_result = findViewById(R.id.ll_assessment_result);
        ll_assessment_result.setVisibility(View.GONE);
        tv_weight_content = findViewById(R.id.tv_weight_content);
        tv_height_content = findViewById(R.id.tv_height_content);
        tv_bmi_content = findViewById(R.id.tv_bmi_content);
        tv_other_content = findViewById(R.id.tv_other_content);

        //报告解读
        ll_report_interpretation = findViewById(R.id.ll_report_interpretation);
        ll_report_interpretation.setVisibility(View.GONE);
        tv_report_content_1 = findViewById(R.id.tv_report_content_1);
        tv_report_content_2 = findViewById(R.id.tv_report_content_2);
        tv_report_content_3 = findViewById(R.id.tv_report_content_3);
        tv_report_content_4 = findViewById(R.id.tv_report_content_4);

        //提示信息
        rl_prompt_message = findViewById(R.id.rl_prompt_message);
        rl_prompt_message.setVisibility(View.GONE);
        tv_prompt_message = findViewById(R.id.tv_prompt_message);

        //养育指导
        rl_parenting_guide = findViewById(R.id.rl_parenting_guide);
        rl_parenting_guide.setVisibility(View.GONE);
        iv_parenting_guide = findViewById(R.id.iv_parenting_guide);
    }

    private void initData(){
        rg_weight.setOnCheckedChangeListener(diagramOnChangeListener);
        rg_height.setOnCheckedChangeListener(diagramOnChangeListener);
        rg_BMI.setOnCheckedChangeListener(diagramOnChangeListener);
        bt_details.setOnClickListener(diagramListener);

        iv_parenting_guide.setOnClickListener(parentingGuideListener);

        rl_back.setOnClickListener(actionListener);
        tv_title.setText(R.string.assessment);

        initDiagramViewParam();
//        initTestData();
        refreshDiagram(currentIndex);
    }

    private View.OnClickListener actionListener = new View.OnClickListener() {
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

    //------------------------------------------养育指导--------------------------------------------
    private View.OnClickListener parentingGuideListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO 跳转至养育指导
        }
    };


    //------------------------------------------发育行为--------------------------------------------

    //初始化发行为
    private void initDevelopment(Map<String,List<String>> maps){
        Set<String> keys = maps.keySet();
        for (String key:keys){
            ll_developmental_content.addView(createSubItem(key,maps.get(key)),0);
        }
    }

    private View createSubItem(String title , List<String> contents){
        RelativeLayout layout = new RelativeLayout(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);

        View divider = new View(this);
        divider.setBackgroundColor(getResources().getColor(R.color.linkLightPink));
        divider.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelOffset(R.dimen.y2px)));
        layout.addView(divider);

        TextView tv_title = new TextView(this);
        tv_title.setText(title);
        tv_title.setTextColor(getResources().getColor(R.color.colorBlackText));
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y28px));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y35px);
        tv_title.setLayoutParams(params);
        layout.addView(tv_title);

        View right = createCheckLine(contents);
        layout.addView(right);

        return layout;
    }

    private View createCheckLine(List<String> contents){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.y24px);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP|RelativeLayout.ALIGN_PARENT_LEFT);
        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y35px);
        params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x139px);
        layout.setLayoutParams(params);

        for (String content:contents){
            LinearLayout subLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.y10px);
            subLayout.setOrientation(LinearLayout.HORIZONTAL);
            subLayout.setLayoutParams(layoutParams);

            ImageView icon = new ImageView(this);
            icon.setImageResource(R.drawable.icon_choose);
            icon.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x34px),getResources().getDimensionPixelOffset(R.dimen.x36px)));
            subLayout.addView(icon);

            TextView tv_content = new TextView(this);
            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            textParam.leftMargin = getResources().getDimensionPixelOffset(R.dimen.y5px);
            tv_content.setLayoutParams(textParam);
            tv_content.setText(content);
            tv_content.setTextColor(getResources().getColor(R.color.gray));
            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y26px));
            subLayout.addView(tv_content);

            layout.addView(subLayout);
        }

        return layout;
    }

    //--------------------------------------曲线图界面----------------------------------------------
    private int currentIndex;

    private ImageView iv_details;
    private DiagramView diagram_view;
    private RelativeLayout rl_data;
    private Button bt_details;
    private RadioButton rg_weight,rg_height,rg_BMI;

    private final static String PARAM_WEIGHT = "weight";
    private final static String PARAM_HEIGHT = "height";
    //根据实际月龄判断
    private final static String PARAM_BMI_0_2 = "bmi_0_2";
    private final static String PARAM_BMI_2_5 = "bmi_2_5";
    private Map<String,DiagramView.DiagramBaseInfo> baseParamMap = new HashMap<>();
    private Map<String,DiagramView.DiagramParam> paramMap = new HashMap<>();
    private List<RadioButton> diagramButtons = new ArrayList<>();
    private void initDiagramViewParam(){
        currentIndex = R.id.rg_weight;

        DiagramView.DiagramBaseInfo weightParam = new DiagramView.DiagramBaseInfo();
        weightParam.setLowerLimitX(0);
        weightParam.setLowerLimitY(0);
        weightParam.setUpperLimitX(36);
        weightParam.setUpperLimitY(30);
        weightParam.setxLength(getResources().getDimension(R.dimen.x600px));
        weightParam.setyLength(getResources().getDimension(R.dimen.x622px));
        baseParamMap.put(PARAM_WEIGHT,weightParam);

        DiagramView.DiagramBaseInfo heightParam = new DiagramView.DiagramBaseInfo();
        heightParam.setLowerLimitX(0);
        heightParam.setUpperLimitX(36);
        heightParam.setLowerLimitY(45);
        heightParam.setUpperLimitY(120);
        heightParam.setxLength(getResources().getDimension(R.dimen.x600px));
        heightParam.setyLength(getResources().getDimension(R.dimen.x622px));
        baseParamMap.put(PARAM_HEIGHT,heightParam);


        DiagramView.DiagramBaseInfo bmi0_2Param = new DiagramView.DiagramBaseInfo();
        bmi0_2Param.setLowerLimitX(45);
        bmi0_2Param.setUpperLimitX(110);
        bmi0_2Param.setLowerLimitY(0);
        bmi0_2Param.setUpperLimitY(30);
        bmi0_2Param.setxLength(getResources().getDimension(R.dimen.x590px));
        bmi0_2Param.setyLength(getResources().getDimension(R.dimen.x622px));
        baseParamMap.put(PARAM_BMI_0_2,bmi0_2Param);

        DiagramView.DiagramBaseInfo bmi2_5Param = new DiagramView.DiagramBaseInfo();
        bmi2_5Param.setLowerLimitX(65);
        bmi2_5Param.setUpperLimitX(120);
        bmi2_5Param.setLowerLimitY(0);
        bmi2_5Param.setUpperLimitY(30);
        bmi2_5Param.setxLength(getResources().getDimension(R.dimen.x588px));
        bmi2_5Param.setyLength(getResources().getDimension(R.dimen.x622px));
        baseParamMap.put(PARAM_BMI_2_5,bmi2_5Param);
    }

    private void initTestData(){
        initTestData(PARAM_HEIGHT,45,0);
        initTestData(PARAM_WEIGHT,0,0);
        initTestData(PARAM_BMI_0_2,0,45);
    }

    private void initTestData(String type,int yBase,int xBase){
        DiagramView.DiagramParam param = new DiagramView.DiagramParam();

        List<DiagramView.DiagramPoint> points = new ArrayList<>();
        int x = xBase;
        int y = yBase;
        for (int i = 0;i<8;i++){
            DiagramView.DiagramPoint point = new DiagramView.DiagramPoint();
            if ( i != 0 ){
                x = x +(5);
                y = y +(i /2);
            }
            point.setX(x);
            point.setY(y);
            point.setType(i%2);
            points.add(point);
        }
        param.setBlueLine(points);

        points = new ArrayList<>();
        x = xBase;
        y = yBase;
        for (int i = 0;i<8;i++){
            if ( i > 5 ){
                points.add(param.getBlueLine().get(i));
                continue;
            }
            DiagramView.DiagramPoint point = new DiagramView.DiagramPoint();
            if ( i != 0 ) {
                x = x +(5);
                y = y +(i /3);
            }
            point.setX(x);
            point.setY(y);
            point.setType(i%2);
            points.add(point);
        }
        param.setRedLine(points);

        paramMap.put(type,param);
    }

    //曲线图详细界面跳转
    private View.OnClickListener diagramListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO
        }
    };

    private CompoundButton.OnCheckedChangeListener diagramOnChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if ( isChecked ){
                refreshDiagram(compoundButton.getId());
            }
        }
    };

    //刷新曲线图
    private void refreshDiagram(int id){
        for (RadioButton button:diagramButtons){
            if ( button.getId() != id ){
                button.setChecked(false);
            }
        }

        switch (id){
            case R.id.rg_weight:
                iv_details.setImageResource(R.drawable.bg_boy_weight_data);
                rl_data.setBackgroundColor(getResources().getColor(R.color.bgWeightColor));
                diagram_view.setParam(baseParamMap.get(PARAM_WEIGHT),paramMap.get(PARAM_WEIGHT));
                break;
            case R.id.rg_height:
                iv_details.setImageResource(R.drawable.bg_boy_height_data);
                rl_data.setBackgroundColor(getResources().getColor(R.color.bgHeightColor));
                diagram_view.setParam(baseParamMap.get(PARAM_HEIGHT),paramMap.get(PARAM_HEIGHT));
                break;
            case R.id.rg_BMI:
                iv_details.setImageResource(R.drawable.bg_boy_height_weight_0_2);
                rl_data.setBackgroundColor(getResources().getColor(R.color.bgBMIColor));
                diagram_view.setParam(baseParamMap.get(PARAM_BMI_0_2),paramMap.get(PARAM_BMI_0_2));
                break;
        }
    }

}
