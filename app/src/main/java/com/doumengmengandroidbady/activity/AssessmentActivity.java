package com.doumengmengandroidbady.activity;

import android.content.Intent;
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
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.db.DaoManager;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.response.Doctor;
import com.doumengmengandroidbady.response.Hospital;
import com.doumengmengandroidbady.response.ImageData;
import com.doumengmengandroidbady.response.Record;
import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.util.FormulaUtil;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.view.CircleImageView;
import com.doumengmengandroidbady.view.DiagramView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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

    public final static String IN_PARAM_RECORD = "in_param_record";

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
        initListener();
    }

    private void initListener(){
        rg_weight.setOnCheckedChangeListener(diagramOnChangeListener);
        rg_height.setOnCheckedChangeListener(diagramOnChangeListener);
        rg_BMI.setOnCheckedChangeListener(diagramOnChangeListener);
        bt_details.setOnClickListener(diagramListener);
        iv_parenting_guide.setOnClickListener(parentingGuideListener);
        rl_back.setOnClickListener(actionListener);
    }

    private void initData(){
        tv_title.setText(R.string.assessment);
        initDiagramViewParam();

        Intent intent = getIntent();
        String recordString = intent.getStringExtra(IN_PARAM_RECORD);
        userData = BaseApplication.getInstance().getUserData();
        record = GsonUtil.getInstance().getGson().fromJson(recordString,Record.class);
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
        refreshDiagram(currentIndex);
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
        tv_BMI.setText(FormulaUtil.formulaBMI(Float.parseFloat(height),Float.parseFloat(weight))+"");
    }

    private void initDiagram(){
        DiagramView.DiagramParam weightParam = new DiagramView.DiagramParam();
        DiagramView.DiagramParam heightParam = new DiagramView.DiagramParam();
        DiagramView.DiagramParam heightWeightParam = new DiagramView.DiagramParam();

        List<ImageData> list = record.getImageData();
        for (ImageData imageData:list){
            int currentDayInYear = getDayInYear(imageData.getMonthAge(),imageData.getMonthDay());
            int correntDayInYear = getDayInYear(imageData.getCorrectMonthAge(),imageData.getCorrectMonthDay());
            int height = (int) (Float.parseFloat(imageData.getHeight()) * 100);
            int weight = (int) (Float.parseFloat(imageData.getWeight()) * 100);
            int type = imageData.getType();

            weightParam.getBlueLine().add(new DiagramView.DiagramPoint(currentDayInYear,weight,type));
            weightParam.getRedLine().add(new DiagramView.DiagramPoint(correntDayInYear, weight, type));

            heightParam.getBlueLine().add(new DiagramView.DiagramPoint(currentDayInYear,height,type));
            heightParam.getRedLine().add(new DiagramView.DiagramPoint(correntDayInYear, height, type));

            heightWeightParam.getBlueLine().add(new DiagramView.DiagramPoint(height,weight,type));
        }

        paramMap.put(PARAM_WEIGHT,weightParam);
        paramMap.put(PARAM_HEIGHT,heightParam);

        if ( isLowerThanTwoYear() ) {
            paramMap.put(PARAM_HEIGHT_DEVIDE_WEIGHT_0_2, heightWeightParam);
        } else {
            paramMap.put(PARAM_HEIGHT_DEVIDE_WEIGHT_2_5, heightWeightParam);
        }
    }

    /**
     * 作者: 边贤君
     * 描述: 初始化发育行为
     * 日期: 2018/1/16 16:37
     */
    private void initDevelopmentalBehavior(){
        tv_developmental_title.setText("发育行为（"+record.getCorrectMonthAge()+"个月）");
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
            tv_prompt_message.setText("72小时内看到医生的养育指导和评估");
        } else if ( "5".equals(record.getRecordStatus()) ){
            rl_prompt_message.setVisibility(View.VISIBLE);
            tv_prompt_message.setText("抱歉，医生未能按约给予评估及指导，\n" +
                    "已为您退款或在途中！");
        } else {
            rl_prompt_message.setVisibility(View.GONE);
        }
    }

    /**
     * 作者: 边贤君
     * 描述: 获取实际天数
     * 参数:
     * 返回:
     * 日期: 2018/1/16 15:54
     */
    private int getDayInYear(int month , int day){
        int dayInYear = month * 30;
        if ( day > 30 ){
            dayInYear += 30;
        } else {
            dayInYear += day;
        }
        return dayInYear;
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
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(MainActivity.SHOW_PAGE,MainActivity.PAGE_SPACIALIST_SERVICE);
        startActivity(intent);
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
            tv_content.setTextColor(getResources().getColor(R.color.fourthGray));
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
    private final static String PARAM_HEIGHT_DEVIDE_WEIGHT_0_2 = "bmi_0_2";
    private final static String PARAM_HEIGHT_DEVIDE_WEIGHT_2_5 = "bmi_2_5";
    private Map<String,DiagramView.DiagramBaseInfo> baseParamMap = new HashMap<>();
    private Map<String,DiagramView.DiagramParam> paramMap = new HashMap<>();
    private List<RadioButton> diagramButtons = new ArrayList<>();
    private void initDiagramViewParam(){
        currentIndex = R.id.rg_weight;

        DiagramView.DiagramBaseInfo weightParam = new DiagramView.DiagramBaseInfo();
        weightParam.setLowerLimitX(0);
        weightParam.setLowerLimitY(0);
        weightParam.setUpperLimitX(1080);
        weightParam.setUpperLimitY(3000);
        weightParam.setxLength(getResources().getDimension(R.dimen.x600px));
        weightParam.setyLength(getResources().getDimension(R.dimen.x622px));
        baseParamMap.put(PARAM_WEIGHT,weightParam);

        DiagramView.DiagramBaseInfo heightParam = new DiagramView.DiagramBaseInfo();
        heightParam.setLowerLimitX(0);
        heightParam.setUpperLimitX(1080);
        heightParam.setLowerLimitY(4500);
        heightParam.setUpperLimitY(12000);
        heightParam.setxLength(getResources().getDimension(R.dimen.x600px));
        heightParam.setyLength(getResources().getDimension(R.dimen.x622px));
        baseParamMap.put(PARAM_HEIGHT,heightParam);


        DiagramView.DiagramBaseInfo bmi0_2Param = new DiagramView.DiagramBaseInfo();
        bmi0_2Param.setLowerLimitX(4500);
        bmi0_2Param.setUpperLimitX(11000);
        bmi0_2Param.setLowerLimitY(0);
        bmi0_2Param.setUpperLimitY(3000);
        bmi0_2Param.setxLength(getResources().getDimension(R.dimen.x590px));
        bmi0_2Param.setyLength(getResources().getDimension(R.dimen.x622px));
        baseParamMap.put(PARAM_HEIGHT_DEVIDE_WEIGHT_0_2,bmi0_2Param);

        DiagramView.DiagramBaseInfo bmi2_5Param = new DiagramView.DiagramBaseInfo();
        bmi2_5Param.setLowerLimitX(6500);
        bmi2_5Param.setUpperLimitX(12000);
        bmi2_5Param.setLowerLimitY(0);
        bmi2_5Param.setUpperLimitY(3000);
        bmi2_5Param.setxLength(getResources().getDimension(R.dimen.x588px));
        bmi2_5Param.setyLength(getResources().getDimension(R.dimen.x622px));
        baseParamMap.put(PARAM_HEIGHT_DEVIDE_WEIGHT_2_5,bmi2_5Param);
    }

    //曲线图详细界面跳转
    private View.OnClickListener diagramListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO
            List<ImageData> data = record.getImageData();
            if ( data != null ) {
                String imageData = GsonUtil.getInstance().getGson().toJson(data);
                int type = 0;
                if ( rg_height.isChecked() ){
                    type = DiagramDataActivity.DIAGRAM_TYPE.TYPE_HEIGHT.ordinal();
                } else if ( rg_weight.isChecked() ){
                    type = DiagramDataActivity.DIAGRAM_TYPE.TYPE_WEIGHT.ordinal();
                } else {
                    type = DiagramDataActivity.DIAGRAM_TYPE.TYPE_HEIGHT_WEIGHT.ordinal();
                }
                Intent intent = new Intent(AssessmentActivity.this, DiagramDataActivity.class);
                intent.putExtra(DiagramDataActivity.IN_PARAM_IMAGE_DATA, imageData);
                intent.putExtra(DiagramDataActivity.IN_PARAM_TYPE,type);
                startActivity(intent);
            } else {
                //TODO
            }
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
                refreshWeight();
                break;
            case R.id.rg_height:
                refreshHeight();
                break;
            case R.id.rg_BMI:
                refreshHeightWeight();
                break;
        }
    }

    private void refreshWeight(){
        if ( userData.isMale() ) {
            iv_details.setImageResource(R.drawable.bg_boy_weight_data);
        } else {
            iv_details.setImageResource(R.drawable.bg_girl_weight_data);
        }
        rl_data.setBackgroundColor(getResources().getColor(R.color.bgWeightColor));
        diagram_view.setParam(baseParamMap.get(PARAM_WEIGHT),paramMap.get(PARAM_WEIGHT));
    }

    private void refreshHeight(){
        if ( userData.isMale() ) {
            iv_details.setImageResource(R.drawable.bg_boy_height_data);
        } else {
            iv_details.setImageResource(R.drawable.bg_girl_height_data);
        }
        rl_data.setBackgroundColor(getResources().getColor(R.color.bgHeightColor));
        diagram_view.setParam(baseParamMap.get(PARAM_HEIGHT),paramMap.get(PARAM_HEIGHT));
    }

    private void refreshHeightWeight(){
        if ( !isLowerThanTwoYear() ){
            if ( userData.isMale() ) {
                iv_details.setImageResource(R.drawable.bg_boy_height_weight_2_5);
            } else {
                iv_details.setImageResource(R.drawable.bg_girl_height_weight_2_5);
            }
            diagram_view.setParam(baseParamMap.get(PARAM_HEIGHT_DEVIDE_WEIGHT_2_5),paramMap.get(PARAM_HEIGHT_DEVIDE_WEIGHT_2_5));
        } else {
            if ( userData.isMale() ) {
                iv_details.setImageResource(R.drawable.bg_boy_height_weight_0_2);
            } else {
                iv_details.setImageResource(R.drawable.bg_girl_height_weight_0_2);
            }
            diagram_view.setParam(baseParamMap.get(PARAM_HEIGHT_DEVIDE_WEIGHT_0_2),paramMap.get(PARAM_HEIGHT_DEVIDE_WEIGHT_0_2));
        }
        rl_data.setBackgroundColor(getResources().getColor(R.color.bgBMIColor));
    }

    private boolean isLowerThanTwoYear(){
        int month = Integer.parseInt(record.getMonthAge());
        if ( month > 24 ){
            return false;
        }
        return true;
    }

    //------------------------------更新状态--------------------------------------------------------

    private RequestTask updateRequestTask;

    private RequestCallBack callBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {
            return UrlAddressList.URL_UPDATE_RECORD_STATE;
        }

        @Override
        public Map<String, String> getContent() {
            Map<String,String> map = new HashMap<>();
//            map.put(UrlAddressList.PARAM,userData.);
            map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
            return null;
        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {

        }

        @Override
        public int type() {
            return 0;
        }
    };

}
