package com.doumengmeng.doctor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.PictureAdapter;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.base.BaseTimeActivity;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.request.entity.RequestAssessment;
import com.doumengmeng.doctor.response.AssessmentDetailResponse;
import com.doumengmeng.doctor.response.EvalutionResponse;
import com.doumengmeng.doctor.response.entity.AssessmentItem;
import com.doumengmeng.doctor.response.entity.Nurture;
import com.doumengmeng.doctor.util.FormulaUtil;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.util.MyDialog;
import com.doumengmeng.doctor.view.GraphModule;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Administrator on 2018/3/1.
 */

public class AssessmentActivity extends BaseTimeActivity {

    public final static String IN_PARAM_ASSESSMENT_ITEM = "item";


    private RelativeLayout rl_back , rl_complete;
    private TextView tv_title;

    private TextView tv_over_time;

    private AssessmentDetailResponse.Result dataResult;
    private AssessmentItem item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        loadAssessmentItem();
//        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(searchItemDetailTask);
        stopTask(evaluationTask);
    }

    private void loadAssessmentItem(){
        Intent intent = getIntent();
        item = GsonUtil.getInstance().fromJson(intent.getStringExtra(IN_PARAM_ASSESSMENT_ITEM),AssessmentItem.class);
        getAssessmentData();
    }

    private RequestTask searchItemDetailTask;
    private void getAssessmentData(){
        try {
            searchItemDetailTask = new RequestTask.Builder(this, searchItemDetailCallBack)
                    .setContent(buildRequest())
                    .setType(RequestTask.DEFAULT)
                    .setUrl(UrlAddressList.URL_SEARCH_ASSESSMENT_DETAIL)
                    .build();
            searchItemDetailTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public Map<String,String> buildRequest(){
        Map<String,String> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            object.put("recordId",item.getRecordId());
            object.put("doctorId",BaseApplication.getInstance().getUserData().getDoctorId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(UrlAddressList.PARAM,object.toString());
        map.put(UrlAddressList.SESSION_ID, BaseApplication.getInstance().getSessionId());
        return map;
    }

    private RequestCallBack searchItemDetailCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(AssessmentActivity.this, ResponseErrorCode.getErrorMsg(result), new MyDialog.PromptDialogCallback() {
                @Override
                public void sure() {
                    back();
                }
            });
        }

        @Override
        public void onPostExecute(String result) {
            AssessmentDetailResponse response = GsonUtil.getInstance().fromJson(result,AssessmentDetailResponse.class);
            dataResult = response.getResult();
            initView();
        }
    };

    private void initView(){
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
        initScrollView();
        minuteCallBack();
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

        rl_prompt_close.setOnClickListener(listener);
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
        tv_parity_count = findViewById(R.id.tv_parity_count);
        tv_birth_count = findViewById(R.id.tv_birth_count);
        tv_mon_age = findViewById(R.id.tv_mon_age);
        tv_born_type = findViewById(R.id.tv_born_type);
        tv_delivery_methods = findViewById(R.id.tv_delivery_methods);
        tv_assisted_reproductive = findViewById(R.id.tv_assisted_reproductive);
        tv_birth_injury = findViewById(R.id.tv_birth_injury);
        tv_neonatalasphyxia = findViewById(R.id.tv_neonatalasphyxia);
        tv_intracranial_hemorrhage = findViewById(R.id.tv_intracranial_hemorrhage);
        tv_hereditary_history = findViewById(R.id.tv_hereditary_history);
        tv_allergic_history = findViewById(R.id.tv_allergic_history);
        tv_past_history = findViewById(R.id.tv_past_history);

        AssessmentDetailResponse.User user = dataResult.getUserList();
        tv_baby_name.setText(user.getTruename());
        tv_baby_gender.setText(user.getSexString());
        tv_birthday.setText(user.getBirthday());
        tv_pregnancy_weeks.setText(String.format(getString(R.string.format_pregnancy_weeks),user.getPregnancyweeks(),user.getPregnancydays()));
        tv_born_weight.setText(String.format(getString(R.string.format_kg),user.getBornweight()));
        tv_born_height.setText(String.format(getString(R.string.format_cm),user.getBornheight()));
        tv_parity_count.setText(user.getPregnancies());
        tv_birth_count.setText(user.getBirthtimes());
        tv_mon_age.setText(user.getMumbearage());
        tv_born_type.setText(user.getBorntypeString());
        tv_delivery_methods.setText(user.getDeliverymethods());
        tv_assisted_reproductive.setText(user.getAssistedreproductiveString());
        tv_birth_injury.setText(user.getBirthinjuryString());
//        tv_neonatalasphyxia.setText(user.getNeonatalasphyxiaString());
//        tv_intracranial_hemorrhage.setText(user.getIntracranialhemorrhageString());
        tv_hereditary_history.setText(user.getHereditaryhistoryString());
        tv_allergic_history.setText(user.getAllergichistoryString());
        tv_past_history.setText(user.getPasthistory());
    }

    private TextView tv_current_month,tv_correct_month,tv_height,tv_weight,tv_head,
            tv_chest,tv_night_sleep,tv_day_sleep,tv_cacation,tv_micturition;
    private TextView tv_height_reference_value,tv_weight_reference_value,
            tv_head_reference_value,tv_chest_reference_value,tv_night_sleep_reference_value,
            tv_day_sleep_reference_value;
    private RelativeLayout rl_correct_month;
    private void initMeasureInfo(){
        rl_correct_month = findViewById(R.id.rl_correct_month);
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

        AssessmentDetailResponse.Record record = dataResult.getRecordList();
        tv_current_month.setText(String.format(getString(R.string.format_month_age),record.getMonthage(),record.getMonthday()));
        tv_correct_month.setText(String.format(getString(R.string.format_month_age),record.getCorrectmonthage(),record.getCorrectmonthday()));

        if ( record.getMonthage() == record.getCorrectmonthage() && record.getMonthday() == record.getCorrectmonthday() ){
            rl_correct_month.setVisibility(View.INVISIBLE);
        } else {
            rl_correct_month.setVisibility(View.VISIBLE);
        }

        tv_height.setText(String.format(getString(R.string.format_cm),record.getHeight()));
        tv_weight.setText(String.format(getString(R.string.format_kg),record.getWeight()));
        tv_head.setText(String.format(getString(R.string.format_head),record.getHeadcircumference()));
        tv_chest.setText(String.format(getString(R.string.format_chest),record.getChestcircumference()));
        tv_night_sleep.setText(String.format(getString(R.string.format_sleep),record.getNighttimesleep()));
        tv_day_sleep.setText(String.format(getString(R.string.format_sleep),record.getDaytimesleep()));
        tv_cacation.setText(String.format(getString(R.string.format_cacation),record.getCacationdays(),record.getCacation()));
        tv_micturition.setText(String.format(getString(R.string.format_micturition),record.getUrinate()));

        AssessmentDetailResponse.User user = dataResult.getUserList();
        String height = null;
        String weight = null;
        String head = null;
        String chest = null;
        String night = null;
        String day = null;
        if ( user.isMale() ){
            height = getResources().getStringArray(R.array.height_reference_boy)[record.getMonthage()>35?35:record.getMonthage()];
            weight = getResources().getStringArray(R.array.weight_reference_boy)[record.getMonthage()>35?35:record.getMonthage()];
            head = getResources().getStringArray(R.array.head_reference_boy)[record.getMonthage()>35?35:record.getMonthage()];
            chest = getResources().getStringArray(R.array.chest_reference_boy)[record.getMonthage()>35?35:record.getMonthage()];
        } else {
            height = getResources().getStringArray(R.array.height_reference_girl)[record.getMonthage()>35?35:record.getMonthage()];
            weight = getResources().getStringArray(R.array.weight_reference_girl)[record.getMonthage()>35?35:record.getMonthage()];
            head = getResources().getStringArray(R.array.head_reference_girl)[record.getMonthage()>35?35:record.getMonthage()];
            chest = getResources().getStringArray(R.array.chest_reference_boy)[record.getMonthage()>35?35:record.getMonthage()];
        }
        night = getResources().getStringArray(R.array.night_sleep_reference)[record.getMonthage()>35?35:record.getMonthage()];
        day = getResources().getStringArray(R.array.day_sleep_reference)[record.getMonthage()>35?35:record.getMonthage()];

        tv_height_reference_value.setText(String.format(getString(R.string.format_cm),height));
        tv_weight_reference_value.setText(String.format(getString(R.string.format_kg),weight));
        tv_head_reference_value.setText(String.format(getString(R.string.format_cm),head));
        tv_chest_reference_value.setText(String.format(getString(R.string.format_cm),chest));
        tv_night_sleep_reference_value.setText(String.format(getString(R.string.format_hour),night));
        tv_day_sleep_reference_value.setText(String.format(getString(R.string.format_hour),day));

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

        AssessmentDetailResponse.Record record = dataResult.getRecordList();
        tv_human_feed.setText(String.format(getString(R.string.format_feed_milk),record.getBreastfeedingml()));
        tv_human_feed_count.setText(String.format(getString(R.string.format_feed_milk_count),record.getBreastfeedingcount()));
        tv_human_feed_all.setText(String.format(getString(R.string.format_feed_milk_day),Integer.parseInt(record.getBreastfeedingcount())*Integer.parseInt(record.getBreastfeedingml())));
        tv_formula_feed.setText(String.format(getString(R.string.format_feed_milk),record.getMilkfeedingml()));
        tv_formula_feed_count.setText(String.format(getString(R.string.format_feed_milk_count),record.getMilkfeedingcount()));
        tv_formula_feed_all.setText(String.format(getString(R.string.format_feed_milk_day),Integer.parseInt(record.getMilkfeedingml())*Integer.parseInt(record.getMilkfeedingcount())));
        tv_feed_all.setText(String.format(getString(R.string.format_feed_milk_day),Integer.parseInt(record.getMilkfeedingml())*Integer.parseInt(record.getMilkfeedingcount())+Integer.parseInt(record.getBreastfeedingcount())*Integer.parseInt(record.getBreastfeedingml())));
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

        AssessmentDetailResponse.SupplementFood supplementFood = dataResult.getSupplementaryFoodList();
        tv_milk.setText(String.format(getString(R.string.format_ml_day),supplementFood.getMilk()));
        //TODO
//        tv_milk_powder.setText(String.format(getString(R.string.format_ml_day),supplementFood.getPasta()));
        tv_rice_flour.setText(String.format(getString(R.string.format_gram_day),supplementFood.getRiceflour()));
        tv_food.setText(String.format(getString(R.string.format_gram_day),supplementFood.getPasta()));
        tv_porridge.setText(String.format(getString(R.string.format_gram_day),supplementFood.getCongee()));
        tv_rice.setText(String.format(getString(R.string.format_gram_day),supplementFood.getRice()));
        tv_meat.setText(String.format(getString(R.string.format_gram_day),supplementFood.getMeat()));
        tv_egg.setText(String.format(getString(R.string.format_gram_day),supplementFood.getEggs()));
        tv_bean.setText(String.format(getString(R.string.format_gram_day),supplementFood.getSoyproducts()));
        tv_vegetables.setText(String.format(getString(R.string.format_gram_day),supplementFood.getVegetables()));
        tv_fruit.setText(String.format(getString(R.string.format_count_day),supplementFood.getFruit()));
        tv_water.setText(String.format(getString(R.string.format_ml_day),supplementFood.getWater()));
        tv_vitamin.setText(String.format(getString(R.string.format_mg_day),supplementFood.getVitaminaddose()));
        tv_calcium.setText(String.format(getString(R.string.format_gram_day),supplementFood.getCalciumdose()));
        tv_other.setText(supplementFood.getOtherfood());
        tv_appetite.setText(supplementFood.getOrexia());
    }

    private TextView tv_message_board;
    private void initMessageBoard(){
        tv_message_board = findViewById(R.id.tv_message_board);
        tv_message_board.setText(dataResult.getRecordList().getOther());
    }

    private GridView gv_upload_report;
    private PictureAdapter adapter;
    private void initPictureView(){
        gv_upload_report = findViewById(R.id.gv_upload_report);
        adapter = new PictureAdapter(dataResult.getReportImgList());
        gv_upload_report.setAdapter(adapter);
        setGridViewHeight(gv_upload_report);
        adapter.notifyDataSetChanged();
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
        graph_module.setData(dataResult.getImgList(),dataResult.getRecordList().getMonthage(),dataResult.getUserList().isMale(),false);
    }

    private TextView tv_developmental_title;
    private LinearLayout ll_developmental_content;
    private void initDevelopment(){
        tv_developmental_title = findViewById(R.id.tv_developmental_title);
        ll_developmental_content = findViewById(R.id.ll_developmental_content);

        AssessmentDetailResponse.Record record = dataResult.getRecordList();
        tv_developmental_title.setText(String.format(getResources().getString(R.string.assessment_develop_month),record.getCorrectmonthage()<0?0:record.getCorrectmonthage()));
        Map<String,List<String>> maps = DaoManager.getInstance().getFeatureDao().searchFeatureListById(this,record.getFeature());
        initDevelopment(maps);
    }

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

        AssessmentDetailResponse.Record record = dataResult.getRecordList();
        tv_weight_content.setText(record.getWeightevaluation());
        tv_height_content.setText(record.getHeightevaluation());
        tv_bmi_content.setText(record.getHwevaluation());
        tv_other_content.setText(record.getFeatureevaluation());
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
    private LinearLayout ll_parenting_guide_content;
    private TextView tv_edit;
    private List<Nurture> nurtures = new ArrayList<>();
    private void initParentingGuide(){
        bt_parenting_guide = findViewById(R.id.bt_parenting_guide);
        ll_parenting_guide = findViewById(R.id.ll_parenting_guide);
        ll_parenting_guide_content = findViewById(R.id.ll_parenting_guide_content);
        tv_edit = findViewById(R.id.tv_edit);

        tv_edit.setOnClickListener(listener);
        bt_parenting_guide.setOnClickListener(listener);
    }

    private ScrollView sv;
    private void initScrollView(){
//        sv = findViewById(R.id.sv);
//        sv.scrollTo(0,0);
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
                case R.id.bt_parenting_guide:
                    goParentingGuideActivity();
                    break;
                case R.id.tv_edit:
                    goParentingGuideActivity();
                    break;
                case R.id.rl_prompt_close:
                    closePrompt();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private RequestTask evaluationTask;
    private void submitAssessment(){
        if ( checkAssessment() ){
            try {
                evaluationTask = new RequestTask.Builder(this,evaluationCallBack)
                        .setUrl(UrlAddressList.URL_EVALUTION)
                        .setContent(buildRequestEvalution())
                        .setType(RequestTask.DEFAULT)
                        .build();
                evaluationTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private Map<String,String> buildRequestEvalution(){
        RequestAssessment assessment = new RequestAssessment();
        assessment.setDoctorId(BaseApplication.getInstance().getUserData().getDoctorId());
        assessment.setRecordId(item.getRecordId());

        RequestAssessment.EvaluationInfo evaluationInfo = new RequestAssessment.EvaluationInfo();
        evaluationInfo.setDoctorAdvice(et_report_content_4.getText().toString().trim());
        evaluationInfo.setFeatureAppraisal(et_report_content_3.getText().toString().trim());
        evaluationInfo.setGrowthLevelAppraisal(et_report_content_2.getText().toString().trim());
        evaluationInfo.setGrowthTendencyAppraisal(et_report_content_1.getText().toString().trim());
        assessment.setEvaluationInfo(evaluationInfo);

        List<RequestAssessment.NurtureDesc> descs = new ArrayList<>();
        for (Nurture nurture:nurtures){
            RequestAssessment.NurtureDesc desc = new RequestAssessment.NurtureDesc();
            if ( nurture.isCustom() ){
                desc.setCustomNurtureDesc(nurture.getNurtureDesc());
                desc.setCustomNurtureTitle(nurture.getNurtureTitle());
                desc.setNurtureId("0");
                desc.setNurtureTypeId(nurture.getNurtureTypeId());
            } else {
                desc.setNurtureId(nurture.getId());
                desc.setNurtureTypeId(nurture.getNurtureTypeId());
                desc.setCustomNurtureTitle("");
                desc.setCustomNurtureDesc("");
            }
            descs.add(desc);
        }
        assessment.setNurtureInfoList(descs);


        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.SESSION_ID,BaseApplication.getInstance().getSessionId());
        map.put(UrlAddressList.PARAM,GsonUtil.getInstance().toJson(assessment));
        return map;
    }

    private RequestCallBack evaluationCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(AssessmentActivity.this, ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            EvalutionResponse response = GsonUtil.getInstance().fromJson(result,EvalutionResponse.class);
            if ( response.getResult() != null && 1 == response.getResult().getIsSuccess() ){
                back();
            } else {
                MyDialog.showPromptDialog(AssessmentActivity.this,"提交失败,请再次尝试",null);
            }
        }
    };


    private boolean checkAssessment(){
        if (TextUtils.isEmpty(et_report_content_1.getText().toString().trim())){
            showPrompt("请填写生长趋势");
            return false;
        }
        if (TextUtils.isEmpty(et_report_content_2.getText().toString().trim())){
            showPrompt("请填写生长水平");
            return false;
        }
        if (TextUtils.isEmpty(et_report_content_3.getText().toString().trim())){
            showPrompt("请填写发育评价");
            return false;
        }
        if (TextUtils.isEmpty(et_report_content_4.getText().toString().trim())){
            showPrompt("请填写指导建议");
            return false;
        }
        if ( nurtures == null || nurtures.size() == 0 ){
            showPrompt("请勾选养育指导");
            return false;
        }

        return true;
    }

    private void closePrompt(){
        rl_prompt.setVisibility(View.GONE);
    }

    private void showPrompt(String content){
        rl_prompt.setVisibility(View.VISIBLE);
        tv_prompt.setText(content);
    }

    private void goHospitalReportActivity(){
        Intent intent = new Intent(this,HospitalReportActivity.class);
        intent.putExtra(HospitalReportActivity.IN_PARAM_USER_DATA,GsonUtil.getInstance().toJson(dataResult.getUserList()));
        intent.putExtra(HospitalReportActivity.IN_PARAM_DOCTOR_ID,dataResult.getRecordList().getDoctorid());
        startActivity(intent);
    }

    private void goHistoryReportActivity(){
        Intent intent = new Intent(this,HistoryReportActivity.class);
        intent.putExtra(HistoryReportActivity.IN_PARAM_USER_DATA,GsonUtil.getInstance().toJson(dataResult.getUserList()));
        intent.putExtra(HistoryReportActivity.IN_PARAM_DOCTOR_ID,dataResult.getRecordList().getDoctorid());
        startActivity(intent);
    }

    private void goParentInfoActivity(){
        Intent intent = new Intent(this,ParentInfoActivity.class);
        intent.putExtra(ParentInfoActivity.IN_PARAM_PARENT_INFO,GsonUtil.getInstance().toJson(dataResult.getParentList()));
        startActivity(intent);
    }

    private static final int REQUEST_PARENTING_GUIDE = 0x01;
    private void goParentingGuideActivity(){
        Intent intent = new Intent(this,AssessmentParentingGuideActivity.class);
        intent.putExtra(AssessmentParentingGuideActivity.IN_PARAM_MONTH_AGE,dataResult.getRecordList().getMonthage());
        intent.putExtra(AssessmentParentingGuideActivity.IN_PARAM_VALIDITY_TIME,item.getValidityTime());
        intent.putExtra(AssessmentParentingGuideActivity.IN_PARAM_SELECTED_NURTURE,GsonUtil.getInstance().toJson(nurtures));
        startActivityForResult(intent,REQUEST_PARENTING_GUIDE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( REQUEST_PARENTING_GUIDE == requestCode && Activity.RESULT_OK == resultCode ){
            nurtures = GsonUtil.getInstance().fromJson(data.getStringExtra(AssessmentParentingGuideActivity.OUT_PARAM_SELECTED_NUTRURE),new TypeToken<List<Nurture>>(){}.getType());
            refreshParentingGuide();
        }
    }

    private void refreshParentingGuide(){
        if ( nurtures == null || nurtures.size() == 0 ){
            ll_parenting_guide.setVisibility(View.GONE);
            bt_parenting_guide.setVisibility(View.VISIBLE);
        } else {
            ll_parenting_guide.setVisibility(View.VISIBLE);
            bt_parenting_guide.setVisibility(View.GONE);
            buildParentingGuide();
        }
    }

    private void buildParentingGuide(){
        ll_parenting_guide_content.removeAllViews();
        Map<String,List<Nurture>> nurtureMap = classifyNurtures(nurtures);
        Set<String> keys = nurtureMap.keySet();
        for (String key:keys){
            buildParentingGuideType(ll_parenting_guide_content,key,nurtureMap.get(key));
        }
    }

    private Map<String,List<Nurture>> classifyNurtures(List<Nurture> nurtures){
        Map<String,List<Nurture>> map = new TreeMap<>();
        for (Nurture nurture:nurtures){
            if ( map.containsKey(nurture.getNurtureType()) ){
                map.get(nurture.getNurtureType()).add(nurture);
            } else {
                List<Nurture> type = new ArrayList<>();
                type.add(nurture);
                map.put(nurture.getNurtureType(),type);
            }
        }
        return map;
    }

    private void buildParentingGuideType(LinearLayout parent,String title,List<Nurture> nurtures){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);

        if ( parent.getChildCount() > 0 ){
            params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y28px);
        }
        layout.addView(buildTitle(title));
        buildParentingGuideCustom(layout,nurtures);
        buildParentingGuideNurtures(layout,nurtures);

        parent.addView(layout);
    }

    private View buildTitle(String title){
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x50px);
        textView.setLayoutParams(params);
        textView.setText(title);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y29px));
        textView.setTextColor(getResources().getColor(R.color.first_black));
        return textView;
    }

    private void buildParentingGuideCustom(ViewGroup parent , List<Nurture> nurtures){
        for (Nurture nurture:nurtures){
            if ( nurture.isCustom() ){
                View view = LayoutInflater.from(this).inflate(R.layout.item_customer_parenting_guide,null);
                EditText et_custom_title = view.findViewById(R.id.et_custom_title);
                EditText et_custom_content = view.findViewById(R.id.et_custom_content);
                et_custom_title.setEnabled(false);
                et_custom_content.setEnabled(false);

                et_custom_title.setText(nurture.getNurtureTitle());
                et_custom_content.setText(nurture.getNurtureDesc());

                parent.addView(view);
            }
        }
    }

    private void buildParentingGuideNurtures(ViewGroup parent , List<Nurture> nurtures){
        for (Nurture nurture:nurtures){
            if ( !nurture.isCustom() ) {
                parent.addView(buildParentingGuideNurturesItem(nurture));
            }
        }
    }

    private View buildParentingGuideNurturesItem(Nurture nurture){
        View view = LayoutInflater.from(this).inflate(R.layout.item_nurture_content, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y18px);
        view.setLayoutParams(params);

        LinearLayout ll_content = view.findViewById(R.id.ll_content);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);
        CheckBox cb = view.findViewById(R.id.cb);

        ll_content.setTag(nurture);
        cb.setTag(ll_content);

        tv_title.setText(nurture.getNurtureTitle());
        tv_content.setText(nurture.getNurtureDesc());
        cb.setChecked(nurture.isChoose());
        ll_content.setEnabled(nurture.isChoose());
        cb.setEnabled(false);
        return view;
    }

    @Override
    public void minuteCallBack() {
        if ( tv_over_time != null ){
            String time = FormulaUtil.getTimeDifference(item.getValidityTime());
            if ( time != null ) {
                tv_over_time.setText(time);
            }
        }
    }
}
