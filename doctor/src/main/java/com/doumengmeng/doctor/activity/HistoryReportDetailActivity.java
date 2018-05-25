package com.doumengmeng.doctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.response.AssessmentDetailResponse;
import com.doumengmeng.doctor.response.entity.HistoryReport;
import com.doumengmeng.doctor.util.FormulaUtil;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.view.CircleImageView;
import com.doumengmeng.doctor.view.DevelopmentView;
import com.doumengmeng.doctor.view.GraphModule;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2018/3/1.
 */

public class HistoryReportDetailActivity extends BaseActivity {

    public final static String IN_PARAM_HISTORY_REPORT = "in_history_report";
    public final static String IN_PARAM_USER_DATA = "in_user_data";

    private RelativeLayout rl_back;
    private TextView tv_title;

    private CircleImageView civ_doctor;
    private TextView tv_doctor_name,tv_doctor_hospital,tv_doctor_position;
    private TextView tv_baby_name,tv_baby_gender,tv_baby_birthday,tv_realy_age,tv_correct_age,
            tv_height,tv_weight,tv_BMI;

    private RelativeLayout rl_correct_age;

    //曲线图
    private GraphModule graph_module;


    //评估结果
    private LinearLayout ll_assessment_result;
    private TextView tv_weight_content, tv_height_content,tv_bmi_content,tv_other_content;

    //报告解读
    private LinearLayout ll_report_interpretation;
    private TextView tv_report_content_1 , tv_report_content_2 ,tv_report_content_3 ,tv_report_content_4;

//    //提示信息
//    private RelativeLayout rl_prompt_message;
//    private TextView tv_prompt_message;

    //养育指导
    private RelativeLayout rl_parenting_guide;
    private ImageView iv_parenting_guide;

//    private UserData userData;
    private HistoryReport report;
    private AssessmentDetailResponse.User user;
//    private Doctor doctor;
//    private Hospital hospital;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_report_detail);
        findView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        rl_correct_age = findViewById(R.id.rl_correct_age);
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

        //曲线图
        graph_module = findViewById(R.id.graph_module);


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

//        //提示信息
//        rl_prompt_message = findViewById(R.id.rl_prompt_message);
//        rl_prompt_message.setVisibility(View.GONE);
//        tv_prompt_message = findViewById(R.id.tv_prompt_message);

        //养育指导
        rl_parenting_guide = findViewById(R.id.rl_parenting_guide);
        rl_parenting_guide.setVisibility(View.GONE);
        iv_parenting_guide = findViewById(R.id.iv_parenting_guide);
        initListener();
    }

    private void initListener(){
        iv_parenting_guide.setOnClickListener(parentingGuideListener);
        rl_back.setOnClickListener(actionListener);
    }

    private void initData(){
        //TODO
        tv_title.setText(R.string.assessment);

        Intent intent = getIntent();
        String recordString = intent.getStringExtra(IN_PARAM_HISTORY_REPORT);
        String userString = intent.getStringExtra(IN_PARAM_USER_DATA);
//        userData = BaseApplication.getInstance().getUserData();
        report = GsonUtil.getInstance().fromJson(recordString,HistoryReport.class);
        user = GsonUtil.getInstance().fromJson(userString, AssessmentDetailResponse.User.class);
//        doctor = DaoManager.getInstance().getDaotorDao().searchDoctorById(this, report.getDoctorid());
//        hospital = DaoManager.getInstance().getHospitalDao().searchHospitalById(this,doctor.getDoctorid());

        initDoctorData();
        initUserData();
        initDiagram();
        initDevelopmentalBehavior();
        initAssessmentResult();
        initReportInterpretation();
        initParentingGuide();
//        initPromptMessage();
//        refreshDiagram(currentIndex);
    }

    private void initDoctorData(){
        //TODO
        ImageLoader.getInstance().displayImage(report.getDoctorImg(),civ_doctor,initDisplayImageOptions());
        tv_doctor_name.setText(report.getDoctorName());
        tv_doctor_hospital.setText(report.getHospitalName());
        tv_doctor_position.setText(report.getPositionalTitles());
    }

    private DisplayImageOptions initDisplayImageOptions(){
        DisplayImageOptions.Builder builder = BaseApplication.getInstance().defaultDisplayImage();
        builder.showImageOnFail(R.drawable.default_icon_doctor);
        builder.showImageForEmptyUri(R.drawable.default_icon_doctor);
        builder.showImageOnLoading(R.drawable.default_icon_doctor);
        return builder.build();
    }

    private void initUserData(){
        tv_baby_name.setText(user.getTruename());
        tv_baby_gender.setText(user.getSexString());
        tv_baby_birthday.setText(user.getBirthday());

        tv_realy_age.setText(report.getCurrentMonthAgeString());
        if ( report.getCurrentMonthAgeString().equals(report.getCorrectMonthAgeString()) ){
            rl_correct_age.setVisibility(View.GONE);
        }
        tv_correct_age.setText(report.getCorrectMonthAgeString());
        String height = report.getHeight();
        String weight = report.getWeight();
        tv_height.setText(height);
        tv_weight.setText(weight);
        tv_BMI.setText(String.valueOf(FormulaUtil.formulaBMI(Float.parseFloat(weight),Float.parseFloat(height))));
    }

    private void initDiagram(){
        graph_module.setData(report.getImageData(), report.getMonthAge(),user.isMale(), true);
    }


    //发育行为
//    private LinearLayout ll_developmental_behavior;
//    private LinearLayout ll_developmental_content;
//    private TextView tv_developmental_title;

    private DevelopmentView v_development;
    /**
     * 作者: 边贤君
     * 描述: 初始化发育行为
     * 日期: 2018/1/16 16:37
     */
    private void initDevelopmentalBehavior(){

        String featureAge = String.valueOf(report.getCorrectMonthAge()<0?0: report.getCorrectMonthAge());
        v_development = findViewById(R.id.v_development);
        v_development.initDevelopment(featureAge,report.getRecordTime(),report.getFeatureList(),false);
//        //发育行为
////        ll_developmental_behavior = findViewById(R.id.ll_developmental_behavior);
//        ll_developmental_content = findViewById(R.id.ll_developmental_content);
//        tv_developmental_title = findViewById(R.id.tv_developmental_title);
//
//
//        tv_developmental_title.setText(String.format(getResources().getString(R.string.assessment_develop_month), report.getCorrectMonthAge()<0?0: report.getCorrectMonthAge()));
//
//        initDevelopment(generateDevelopmentData(featureAge,report.getRecordTime(),report.getFeatureList()));

//        Map<String,List<String>> maps = DaoManager.getInstance().getFeatureDao().searchFeatureListById(this, report.getFeatureList());
//        initDevelopment(maps);
    }

    /**
     * 作者: 边贤君
     * 描述: 初始化评估结果
     * 日期: 2018/1/16 16:48
     */
    private void initAssessmentResult(){
        if ( "3".equals(report.getRecordStatus()) || "4".equals(report.getRecordStatus()) ){
            ll_assessment_result.setVisibility(View.VISIBLE);
            tv_weight_content.setText(report.getWeightEvaluation());
            tv_height_content.setText(report.getHeightEvaluation());
            tv_bmi_content.setText(report.getHwEvaluation());
            tv_other_content.setText(report.getFeatureEvaluation());
        } else {
            ll_assessment_result.setVisibility(View.GONE);
        }
    }

    /**
     * 作者: 边贤君
     * 描述: 初始化报告解读
     * 日期: 2018/1/16 16:48
     */
    private void initReportInterpretation(){
        if ( "3".equals(report.getRecordStatus()) || "4".equals(report.getRecordStatus()) ){
            ll_report_interpretation.setVisibility(View.VISIBLE);
            tv_report_content_1.setText(report.getGrowthTendencyAppraisal());
            tv_report_content_2.setText(report.getGrowthLevelAppraisal());
            tv_report_content_3.setText(report.getFeatureAppraisal());
            tv_report_content_4.setText(report.getDoctorAdvice());
        } else {
            ll_report_interpretation.setVisibility(View.GONE);
        }
    }

    /**
     * 作者: 边贤君
     * 描述: 初始化养育指导
     * 日期: 2018/1/16 16:51
     */
    private void initParentingGuide(){
        if ( "3".equals(report.getRecordStatus()) || "4".equals(report.getRecordStatus()) ){
            rl_parenting_guide.setVisibility(View.VISIBLE);
        } else {
            rl_parenting_guide.setVisibility(View.GONE);
        }
    }

//    private void initPromptMessage(){
//        if ( "1".equals(report.getRecordstatus()) || "2".equals(report.getRecordstatus()) ){
//            rl_prompt_message.setVisibility(View.VISIBLE);
//            tv_prompt_message.setText(getResources().getString(R.string.assessment_wait_message));
//        } else if ( "5".equals(report.getRecordstatus()) ){
//            rl_prompt_message.setVisibility(View.VISIBLE);
//            tv_prompt_message.setText(getResources().getString(R.string.assessment_refund_message));
//        } else {
//            rl_prompt_message.setVisibility(View.GONE);
//        }
//    }

    private final View.OnClickListener actionListener = new View.OnClickListener() {
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


    //------------------------------------------发育行为--------------------------------------------

//    private Map<String,List<HistoryReportDetailActivity.DevelopmentalItem>> generateDevelopmentData(String correntMonth , String recordTime, List<String> selected){
//        Map<String,List<HistoryReportDetailActivity.DevelopmentalItem>> map = new HashMap<>();
//
//        List<String> ages = new ArrayList<>();
//        ages.add(correntMonth);
//        List<Feature> features = DaoManager.getInstance().getFeatureDao().searchFeatureList(this,ages,recordTime);
//
//        for (Feature feature:features){
//            List<HistoryReportDetailActivity.DevelopmentalItem> developmentalItems;
//            if ( !map.containsKey(feature.getFeaturetype()) ){
//                developmentalItems = new ArrayList<>();
//                map.put(feature.getFeaturetype(),developmentalItems);
//            } else {
//                developmentalItems = map.get(feature.getFeaturetype());
//            }
//            HistoryReportDetailActivity.DevelopmentalItem item = new HistoryReportDetailActivity.DevelopmentalItem();
//            item.setValue(feature.getDetaildesc());
//            if ( selected != null ) {
//                item.setCheck(selected.contains(feature.getId()));
//            } else {
//                item.setCheck(false);
//            }
//            item.setMark(!"0".equals(feature.getPointtag()));
//            developmentalItems.add(item);
//        }
//        return map;
//    }
//
//    //初始化发行为
//    private void initDevelopment(Map<String,List<HistoryReportDetailActivity.DevelopmentalItem>> maps){
//        Set<String> keys = maps.keySet();
//        for (String key:keys){
//            ll_developmental_content.addView(createSubItem(key,maps.get(key)),0);
//        }
//    }
//
//    /**
//     * 作者: 边贤君
//     * 描述: 创建发育行为条目
//     * 日期: 2018/1/18 9:44
//     */
//    private View createSubItem(String title , List<HistoryReportDetailActivity.DevelopmentalItem> contents){
//        RelativeLayout layout = new RelativeLayout(this);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout.setLayoutParams(layoutParams);
//
//        View divider = new View(this);
//        divider.setBackgroundColor(getResources().getColor(R.color.linePink));
//        divider.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelOffset(R.dimen.y1px)));
//        layout.addView(divider);
//
//        TextView tv_title = new TextView(this);
//        tv_title.setText(title);
//        tv_title.setTextColor(getResources().getColor(R.color.second_black));
//        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y24px));
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y35px);
//        params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.y20px);
//        tv_title.setLayoutParams(params);
//        layout.addView(tv_title);
//
//        View right = createCheckLine(contents);
//        layout.addView(right);
//
//        return layout;
//    }
//
//    /**
//     * 作者: 边贤君
//     * 描述: 创建发育行为条目中的选项
//     * 日期: 2018/1/18 9:44
//     */
//    private View createCheckLine(List<HistoryReportDetailActivity.DevelopmentalItem> contents){
//        LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.y24px);
//        params.addRule(RelativeLayout.ALIGN_PARENT_TOP|RelativeLayout.ALIGN_PARENT_LEFT);
//        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y35px);
//        params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x159px);
//        params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.x20px);
//        layout.setLayoutParams(params);
//
//        for (HistoryReportDetailActivity.DevelopmentalItem content:contents){
//            LinearLayout subLayout = new LinearLayout(this);
//            LinearLayout.LayoutParams layoutParams = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
//            layoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.y10px);
//            subLayout.setOrientation(LinearLayout.HORIZONTAL);
//            subLayout.setLayoutParams(layoutParams);
//
//            CheckBox checkBox = new CheckBox(this);
//            checkBox.setButtonDrawable(null);
//            checkBox.setBackgroundResource(R.drawable.cb_history_report);
//            checkBox.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x34px),getResources().getDimensionPixelOffset(R.dimen.x34px)));
//            checkBox.setChecked(content.isCheck());
//            subLayout.addView(checkBox);
//
//            TextView tv_content = new TextView(this);
//            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//            textParam.leftMargin = getResources().getDimensionPixelOffset(R.dimen.y10px);
//            textParam.topMargin = -1*getResources().getDimensionPixelOffset(R.dimen.y5px);
//            tv_content.setLayoutParams(textParam);
//            if ( content.isMark ) {
//                String value = content.getValue()+"*";
//                SpannableStringBuilder style = new SpannableStringBuilder(value);
//                style.setSpan(new ForegroundColorSpan(Color.RED), value.length()-1, value.length(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                tv_content.setText(style);
//            } else {
//                tv_content.setText(content.getValue());
//            }
//            tv_content.setTextColor(getResources().getColor(R.color.fourth_gray));
//            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y24px));
//            subLayout.addView(tv_content);
//
////            if ( content.isMark ){
////                TextView tv_mark = new TextView(this);
////                LinearLayout.LayoutParams markParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
////                textParam.topMargin = -1*getResources().getDimensionPixelOffset(R.dimen.y5px);
////                tv_mark.setLayoutParams(markParam);
////                tv_mark.setText("*");
////                tv_mark.setTextColor(Color.RED);
////                tv_mark.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y24px));
////                subLayout.addView(tv_mark);
////            }
//
//            layout.addView(subLayout);
//        }
//
//        return layout;
//    }
//
//    public static class DevelopmentalItem{
//        private String value;
//        private boolean isCheck;
//        private boolean isMark;
//
//        public String getValue() {
//            return value;
//        }
//
//        public void setValue(String value) {
//            this.value = value;
//        }
//
//        public boolean isCheck() {
//            return isCheck;
//        }
//
//        public void setCheck(boolean check) {
//            isCheck = check;
//        }
//
//        public boolean isMark() {
//            return isMark;
//        }
//
//        public void setMark(boolean mark) {
//            isMark = mark;
//        }
//    }

//    //初始化发行为
//    private void initDevelopment(Map<String,List<String>> maps){
//        Set<String> keys = maps.keySet();
//        for (String key:keys){
//            ll_developmental_content.addView(createSubItem(key,maps.get(key)),0);
//        }
//    }
//
//    /**
//     * 作者: 边贤君
//     * 描述: 创建发育行为条目
//     * 日期: 2018/1/18 9:44
//     */
//    private View createSubItem(String title , List<String> contents){
//        RelativeLayout layout = new RelativeLayout(this);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout.setLayoutParams(layoutParams);
//
//        View divider = new View(this);
//        divider.setBackgroundColor(getResources().getColor(R.color.linkLightPink));
//        divider.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelOffset(R.dimen.y2px)));
//        layout.addView(divider);
//
//        TextView tv_title = new TextView(this);
//        tv_title.setText(title);
//        tv_title.setTextColor(getResources().getColor(R.color.second_black));
//        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y28px));
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y35px);
//        tv_title.setLayoutParams(params);
//        layout.addView(tv_title);
//
//        View right = createCheckLine(contents);
//        layout.addView(right);
//
//        return layout;
//    }
//
//    /**
//     * 作者: 边贤君
//     * 描述: 创建发育行为条目中的选项
//     * 日期: 2018/1/18 9:44
//     */
//    private View createCheckLine(List<String> contents){
//        LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.y24px);
//        params.addRule(RelativeLayout.ALIGN_PARENT_TOP|RelativeLayout.ALIGN_PARENT_LEFT);
//        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y35px);
//        params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x139px);
//        layout.setLayoutParams(params);
//
//        for (String content:contents){
//            LinearLayout subLayout = new LinearLayout(this);
//            LinearLayout.LayoutParams layoutParams = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
//            layoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.y10px);
//            subLayout.setOrientation(LinearLayout.HORIZONTAL);
//            subLayout.setLayoutParams(layoutParams);
//
//            ImageView icon = new ImageView(this);
//            icon.setImageResource(R.drawable.cb_square_check);
//            icon.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x34px),getResources().getDimensionPixelOffset(R.dimen.x36px)));
//            subLayout.addView(icon);
//
//            TextView tv_content = new TextView(this);
//            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//            textParam.leftMargin = getResources().getDimensionPixelOffset(R.dimen.y5px);
//            tv_content.setLayoutParams(textParam);
//            tv_content.setText(content);
//            tv_content.setTextColor(getResources().getColor(R.color.fourth_gray));
//            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y26px));
//            subLayout.addView(tv_content);
//
//            layout.addView(subLayout);
//        }
//
//        return layout;
//    }
//
//    private boolean isLowerThanTwoYear(){
//        return report.getMonthAge() <= 24;
//    }

    //------------------------------------------养育指导--------------------------------------------
    private final View.OnClickListener parentingGuideListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent obj = new Intent(AssessmentActivity.this,AssessmentParentingGuideActivity.class);
//            obj.putExtra(AssessmentParentingGuideActivity.IN_PARAM_RECORD_ID,record.getRecordid());
//            obj.putExtra(AssessmentParentingGuideActivity.IN_PARAM_DOCTOR_ID,record.getDoctorid());
//            startActivity(obj);
        }
    };

}
