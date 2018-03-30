package com.doumengmengandroidbady.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.db.DaoManager;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.response.entity.Doctor;
import com.doumengmengandroidbady.response.entity.Hospital;
import com.doumengmengandroidbady.response.entity.Record;
import com.doumengmengandroidbady.response.entity.UserData;
import com.doumengmengandroidbady.util.FormulaUtil;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.view.CircleImageView;
import com.doumengmengandroidbady.view.GraphModule;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

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

    public final static String IN_PARAM_RECORD = "in_param_record";

    private RelativeLayout rl_back;
    private TextView tv_title;

    private CircleImageView civ_doctor;
    private TextView tv_doctor_name,tv_doctor_hospital,tv_doctor_position;
    private TextView tv_baby_name,tv_baby_gender,tv_baby_birthday,tv_realy_age,tv_correct_age,
            tv_height,tv_weight,tv_BMI;

    //曲线图
    private GraphModule graph_module;

    //发育行为
//    private LinearLayout ll_developmental_behavior;
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

    private UserData userData;
    private Record record;
    private Doctor doctor;
    private Hospital hospital;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        findView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(updateRequestTask);
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

        //曲线图
        graph_module = findViewById(R.id.graph_module);

        //发育行为
//        ll_developmental_behavior = findViewById(R.id.ll_developmental_behavior);
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
        initListener();
    }

    private void initListener(){
        iv_parenting_guide.setOnClickListener(parentingGuideListener);
        rl_back.setOnClickListener(actionListener);
    }

    private void initData(){
        tv_title.setText(R.string.assessment);

        Intent intent = getIntent();
        String recordString = intent.getStringExtra(IN_PARAM_RECORD);
        userData = BaseApplication.getInstance().getUserData();
        record = GsonUtil.getInstance().fromJson(recordString,Record.class);
        doctor = DaoManager.getInstance().getDaotorDao().searchDoctorById(this,record.getDoctorid());
        hospital = DaoManager.getInstance().getHospitalDao().searchHospitalById(this,doctor.getDoctorid());

        initDoctorData();
        initUserData();
        initDiagram();
        initDevelopmentalBehavior();
        initAssessmentResult();
        initReportInterpretation();
        initParentingGuide();
        initPromptMessage();
//        refreshDiagram(currentIndex);
        updateReadState();
    }

    private void initDoctorData(){
        ImageLoader.getInstance().displayImage(doctor.getDoctorimg(),civ_doctor,initDisplayImageOptions());
        tv_doctor_name.setText(doctor.getDoctorname());
        tv_doctor_hospital.setText(hospital.getHospitalname());
        tv_doctor_position.setText(doctor.getPositionaltitles());
    }

    private DisplayImageOptions initDisplayImageOptions(){
        DisplayImageOptions.Builder builder = BaseApplication.getInstance().defaultDisplayImage();
        builder.showImageOnFail(R.drawable.default_icon_doctor);
        builder.showImageForEmptyUri(R.drawable.default_icon_doctor);
        builder.showImageOnLoading(R.drawable.default_icon_doctor);
        return builder.build();
    }

    private void initUserData(){
        tv_baby_name.setText(userData.getTruename());
        tv_baby_gender.setText(userData.getSexString());
        tv_baby_birthday.setText(userData.getBirthday());

        tv_realy_age.setText(record.getCurrentMonthAgeString());
        tv_correct_age.setText(record.getCorrectMonthAgeString());
        String height = record.getHeight();
        String weight = record.getWeight();
        tv_height.setText(height);
        tv_weight.setText(weight);
        tv_BMI.setText(String.valueOf(FormulaUtil.formulaBMI(Float.parseFloat(height),Float.parseFloat(weight))));
    }

    private void initDiagram(){
        graph_module.setData(record.getImageData(),record.getMonthAge(),userData.isMale(), true);
    }

    /**
     * 作者: 边贤君
     * 描述: 初始化发育行为
     * 日期: 2018/1/16 16:37
     */
    private void initDevelopmentalBehavior(){
        tv_developmental_title.setText(String.format(getResources().getString(R.string.assessment_develop_month),record.getCorrectMonthAge()<0?0:record.getCorrectMonthAge()));
        Map<String,List<String>> maps = DaoManager.getInstance().getFeatureDao().searchFeatureListById(this,record.getFeatureList());
        initDevelopment(maps);
    }

    /**
     * 作者: 边贤君
     * 描述: 初始化评估结果
     * 日期: 2018/1/16 16:48
     */
    private void initAssessmentResult(){
        if ( "3".equals(record.getRecordStatus()) || "4".equals(record.getRecordStatus()) ){
            ll_assessment_result.setVisibility(View.VISIBLE);
            tv_weight_content.setText(record.getWeightEvaluation());
            tv_height_content.setText(record.getHeightEvaluation());
            tv_bmi_content.setText(record.getHwEvaluation());
            tv_other_content.setText(record.getFeatureEvaluation());
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
        if ( "3".equals(record.getRecordStatus()) || "4".equals(record.getRecordStatus()) ){
            ll_report_interpretation.setVisibility(View.VISIBLE);
            tv_report_content_1.setText(record.getGrowthTendencyAppraisal());
            tv_report_content_2.setText(record.getGrowthLevelAppraisal());
            tv_report_content_3.setText(record.getFeatureAppraisal());
            tv_report_content_4.setText(record.getDoctorAdvice());
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
        if ( "3".equals(record.getRecordStatus()) || "4".equals(record.getRecordStatus()) ){
            rl_parenting_guide.setVisibility(View.VISIBLE);
        } else {
            rl_parenting_guide.setVisibility(View.GONE);
        }
    }

    private void initPromptMessage(){
        if ( "1".equals(record.getRecordStatus()) || "2".equals(record.getRecordStatus()) ){
            rl_prompt_message.setVisibility(View.VISIBLE);
            tv_prompt_message.setText(getResources().getString(R.string.assessment_wait_message));
        } else if ( "5".equals(record.getRecordStatus()) ){
            rl_prompt_message.setVisibility(View.VISIBLE);
            tv_prompt_message.setText(getResources().getString(R.string.assessment_refund_message));
        } else {
            rl_prompt_message.setVisibility(View.GONE);
        }
    }

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( KeyEvent.KEYCODE_BACK == keyCode ){
            back();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(MainActivity.SHOW_PAGE,MainActivity.PAGE_SPACIALIST_SERVICE);
        startActivity(intent);
        finish();
    }

    //------------------------------------------养育指导--------------------------------------------
    private final View.OnClickListener parentingGuideListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AssessmentActivity.this,ParentingGuideActivity.class);
            intent.putExtra(ParentingGuideActivity.IN_PARAM_RECORD_ID,record.getRecordId());
            intent.putExtra(ParentingGuideActivity.IN_PARAM_DOCTOR_ID,record.getDoctorid());
            startActivity(intent);
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

    /**
     * 作者: 边贤君
     * 描述: 创建发育行为条目
     * 日期: 2018/1/18 9:44
     */
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
        tv_title.setTextColor(getResources().getColor(R.color.second_black));
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

    /**
     * 作者: 边贤君
     * 描述: 创建发育行为条目中的选项
     * 日期: 2018/1/18 9:44
     */
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
            tv_content.setTextColor(getResources().getColor(R.color.fourth_gray));
            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y26px));
            subLayout.addView(tv_content);

            layout.addView(subLayout);
        }

        return layout;
    }

    //--------------------------------------曲线图界面----------------------------------------------
//    private int currentIndex;
//
//    private ImageView iv_details;
//    private GraphView diagram_view;
//    private RelativeLayout rl_data;
//    private Button bt_details;
//    private RadioButton rg_weight,rg_height,rg_BMI;
//
//    private final static String PARAM_WEIGHT = "weight";
//    private final static String PARAM_HEIGHT = "height";
//    //根据实际月龄判断
//    private final static String PARAM_HEIGHT_DEVIDE_WEIGHT_0_2 = "bmi_0_2";
//    private final static String PARAM_HEIGHT_DEVIDE_WEIGHT_2_5 = "bmi_2_5";
//    private final Map<String, GraphView.GraphBaseInfo> baseParamMap = new HashMap<>();
//    private final Map<String, GraphView.GraphLine> paramMap = new HashMap<>();
//    private final List<RadioButton> diagramButtons = new ArrayList<>();
//    private void initDiagramViewParam(){
//        currentIndex = R.id.rg_weight;
//
//        GraphView.GraphBaseInfo weightParam = new GraphView.GraphBaseInfo();
//        weightParam.setLowerLimitX(0);
//        weightParam.setLowerLimitY(0);
//        weightParam.setUpperLimitX(1080);
//        weightParam.setUpperLimitY(3000);
//        weightParam.setxLength(getResources().getDimension(R.dimen.x600px));
//        weightParam.setyLength(getResources().getDimension(R.dimen.x622px));
//        baseParamMap.put(PARAM_WEIGHT,weightParam);
//
//        GraphView.GraphBaseInfo heightParam = new GraphView.GraphBaseInfo();
//        heightParam.setLowerLimitX(0);
//        heightParam.setUpperLimitX(1080);
//        heightParam.setLowerLimitY(4500);
//        heightParam.setUpperLimitY(12000);
//        heightParam.setxLength(getResources().getDimension(R.dimen.x600px));
//        heightParam.setyLength(getResources().getDimension(R.dimen.x622px));
//        baseParamMap.put(PARAM_HEIGHT,heightParam);
//
//
//        GraphView.GraphBaseInfo bmi0_2Param = new GraphView.GraphBaseInfo();
//        bmi0_2Param.setLowerLimitX(4500);
//        bmi0_2Param.setUpperLimitX(11000);
//        bmi0_2Param.setLowerLimitY(0);
//        bmi0_2Param.setUpperLimitY(3000);
//        bmi0_2Param.setxLength(getResources().getDimension(R.dimen.x590px));
//        bmi0_2Param.setyLength(getResources().getDimension(R.dimen.x622px));
//        baseParamMap.put(PARAM_HEIGHT_DEVIDE_WEIGHT_0_2,bmi0_2Param);
//
//        GraphView.GraphBaseInfo bmi2_5Param = new GraphView.GraphBaseInfo();
//        bmi2_5Param.setLowerLimitX(6500);
//        bmi2_5Param.setUpperLimitX(12000);
//        bmi2_5Param.setLowerLimitY(0);
//        bmi2_5Param.setUpperLimitY(3000);
//        bmi2_5Param.setxLength(getResources().getDimension(R.dimen.x588px));
//        bmi2_5Param.setyLength(getResources().getDimension(R.dimen.x622px));
//        baseParamMap.put(PARAM_HEIGHT_DEVIDE_WEIGHT_2_5,bmi2_5Param);
//    }

//    //曲线图详细界面跳转
//    private final View.OnClickListener diagramListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            List<ImageData> data = record.getImageData();
//            if ( data != null ) {
//                String imageData = GsonUtil.getInstance().toJson(data);
//                int type;
//                if ( rg_height.isChecked() ){
//                    type = DiagramDataActivity.DIAGRAM_TYPE.TYPE_HEIGHT.ordinal();
//                } else if ( rg_weight.isChecked() ){
//                    type = DiagramDataActivity.DIAGRAM_TYPE.TYPE_WEIGHT.ordinal();
//                } else {
//                    type = DiagramDataActivity.DIAGRAM_TYPE.TYPE_HEIGHT_WEIGHT.ordinal();
//                }
//                Intent intent = new Intent(AssessmentActivity.this, DiagramDataActivity.class);
//                intent.putExtra(DiagramDataActivity.IN_PARAM_IMAGE_DATA, imageData);
//                intent.putExtra(DiagramDataActivity.IN_PARAM_TYPE,type);
//                startActivity(intent);
//            } else {
//                Toast.makeText(AssessmentActivity.this,"暂无数据",Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
//    private final CompoundButton.OnCheckedChangeListener diagramOnChangeListener = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//            if ( isChecked ){
//                refreshDiagram(compoundButton.getId());
//            }
//        }
//    };
//
//    //刷新曲线图
//    private void refreshDiagram(int id){
//        for (RadioButton button:diagramButtons){
//            if ( button.getId() != id ){
//                button.setChecked(false);
//            }
//        }
//
//        switch (id){
//            case R.id.rg_weight:
//                refreshWeight();
//                break;
//            case R.id.rg_height:
//                refreshHeight();
//                break;
//            case R.id.rg_BMI:
//                refreshHeightWeight();
//                break;
//        }
//    }
//
//    private void refreshWeight(){
//        if ( userData.isMale() ) {
//            iv_details.setImageResource(R.drawable.bg_boy_weight_data);
//        } else {
//            iv_details.setImageResource(R.drawable.bg_girl_weight_data);
//        }
//        rl_data.setBackgroundColor(getResources().getColor(R.color.bgWeightColor));
//        diagram_view.setParam(baseParamMap.get(PARAM_WEIGHT),paramMap.get(PARAM_WEIGHT));
//    }
//
//    private void refreshHeight(){
//        if ( userData.isMale() ) {
//            iv_details.setImageResource(R.drawable.bg_boy_height_data);
//        } else {
//            iv_details.setImageResource(R.drawable.bg_girl_height_data);
//        }
//        rl_data.setBackgroundColor(getResources().getColor(R.color.bgHeightColor));
//        diagram_view.setParam(baseParamMap.get(PARAM_HEIGHT),paramMap.get(PARAM_HEIGHT));
//    }
//
//    private void refreshHeightWeight(){
//        if ( !isLowerThanTwoYear() ){
//            if ( userData.isMale() ) {
//                iv_details.setImageResource(R.drawable.bg_boy_height_weight_2_5);
//            } else {
//                iv_details.setImageResource(R.drawable.bg_girl_height_weight_2_5);
//            }
//            diagram_view.setParam(baseParamMap.get(PARAM_HEIGHT_DEVIDE_WEIGHT_2_5),paramMap.get(PARAM_HEIGHT_DEVIDE_WEIGHT_2_5));
//        } else {
//            if ( userData.isMale() ) {
//                iv_details.setImageResource(R.drawable.bg_boy_height_weight_0_2);
//            } else {
//                iv_details.setImageResource(R.drawable.bg_girl_height_weight_0_2);
//            }
//            diagram_view.setParam(baseParamMap.get(PARAM_HEIGHT_DEVIDE_WEIGHT_0_2),paramMap.get(PARAM_HEIGHT_DEVIDE_WEIGHT_0_2));
//        }
//        rl_data.setBackgroundColor(getResources().getColor(R.color.bgBMIColor));
//    }

    private boolean isLowerThanTwoYear(){
        return record.getMonthAge() <= 24;
    }

    //------------------------------更新状态--------------------------------------------------------

    private RequestTask updateRequestTask;
    private void updateReadState(){
        if ( "3".equals(record.getRecordStatus()) ) {
            try {
                updateRequestTask = new RequestTask.Builder(this, callBack)
                        .setUrl(UrlAddressList.URL_UPDATE_RECORD_STATE)
                        .setType(RequestTask.JSON)
                        .setContent(buildUpdateRequestContent())
                        .build();
                updateRequestTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private final RequestCallBack callBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            //TODO
        }
    };

    private Map<String, String> buildUpdateRequestContent() {
        Map<String,String> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            object.put("recordid",record.getRecordId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(UrlAddressList.PARAM,object.toString());
        map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
        return map;
    }

}
