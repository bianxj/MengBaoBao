package com.doumengmengandroidbady.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.util.FormatCheckUtil;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.util.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/14.
 */

public class InputInfoActivity extends BaseActivity {

    //-------------------------------Title控件----------------------------------------
    private RelativeLayout rl_back;
    private TextView tv_title;
    private RelativeLayout rl_complete;
    private TextView tv_complete;

    //-------------------------------基本信息控件-------------------------------------
    private EditText et_baby_name;
    private RadioGroup rg_gender;
    private TextView tv_calendar;
    private EditText et_week;
    private EditText et_day;
    private EditText et_phone;
    private EditText et_baby_weight;
    private EditText et_baby_height;
    private EditText et_birth_count;
    private EditText et_birth_age;
    private RadioGroup rg_birth_state;
    private int[] rb_birth_state = new int[]{R.id.rb_single_birth,R.id.rb_spolyembryony};
    private int[] cb_parturition_state = new int[]{R.id.cb_eutocia,R.id.cb_breech,R.id.cb_odinopoeia,R.id.cb_parturition,R.id.cb_cesarean};
    private RadioGroup rg_assisted_reproduction;
    private int[] rb_assisted_reproduction = new int[]{R.id.rb_assisted_none,R.id.rb_artificial_insemination,R.id.rb_tube_baby};
    private EditText et_parity_count;
    private RadioGroup rg_birth_injury;
    private int[] rb_birth_injury = new int[]{R.id.rb_injury_none,R.id.rb_injury_has};
    private RadioGroup rg_stifle;
    private int[] rb_stifle = new int[]{R.id.rb_istifle_none,R.id.rb_stifle_has};
    private RadioGroup rg_ICH;
    private int[] rb_ICH = new int[]{R.id.rb_ICH_none,R.id.rb_ICH_has};
    private RadioGroup rg_genetic_history;
    private int[] rb_genetic_history = new int[]{R.id.rb_genetic_none,R.id.rb_genetic_has};
    private RelativeLayout rl_genetic_history;
    private EditText et_genetic_history;
    private RadioGroup rg_allergy_disease;
    private int[] rb_allergy_disease = new int[]{R.id.rb_allergy_none,R.id.rb_allergy_has};
    private RelativeLayout rl_allergy;
    private EditText et_allergy;

    private RelativeLayout rl_other_disease;
    private EditText et_other_disease;
    private List<CheckBox> diseaseCheckBoxes = new ArrayList<>();
    private int[] diseaseCheckBoxesId = new int[]{
            R.id.cb_choleplania,
            R.id.cb_ACHD,
            R.id.cb_hearing_disorder,
            R.id.cb_rethinopthy,
            R.id.cb_PKU,
            R.id.cb_lower_weight_baby,
            R.id.cb_EUGR,
            R.id.cb_neoplastic_disease,
            R.id.cb_hydrocephalus,
            R.id.cb_CH,
            R.id.cb_enteritis,
            R.id.cb_rickets,
            R.id.cb_anemia,
            R.id.cb_CAH,
            R.id.cb_fat,
            R.id.cb_malnutvrition,
            R.id.cb_develop_lower,
            R.id.cb_epilepsy,
            R.id.cb_genetic_disease,
            R.id.cb_PD,
            R.id.cb_leukemia,
            R.id.cb_asthma,
            R.id.cb_pneumonia,
            R.id.cb_HIE,
            R.id.cb_chronic_disease,
            R.id.cb_none,
            R.id.cb_other_disease,
    };

    //---------------------------父母信息控件----------------------------------------
    private EditText et_father_name;
    private EditText et_father_height;
    private EditText et_father_weight;
    private TextView tv_father_BMI;
    private int[] father_culture_id = new int[]{R.id.rb_father_primary,R.id.rb_father_junior,R.id.rb_father_senior,R.id.rb_father_university,R.id.rb_father_master,R.id.rb_father_doctor};
    private List<RadioButton> father_culture = new ArrayList<>();

    private EditText et_mother_name;
    private EditText et_mother_height;
    private EditText et_mother_weight;
    private TextView tv_mother_BMI;
    private int[] mother_culture_id = new int[]{R.id.rb_mother_primary,R.id.rb_mother_junior,R.id.rb_mother_senior,R.id.rb_mother_university,R.id.rb_mother_master,R.id.rb_mother_doctor};
    private List<RadioButton> mother_culture = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_info);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(submitInfoTask);
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_back.setVisibility(View.GONE);

        tv_title = findViewById(R.id.tv_title);
        rl_complete = findViewById(R.id.rl_complete);
        tv_complete = findViewById(R.id.tv_complete);

        et_baby_name = findViewById(R.id.et_baby_name);
        rg_gender = findViewById(R.id.rg_gender);
        tv_calendar = findViewById(R.id.tv_calendar);
        et_week = findViewById(R.id.et_week);
        et_day = findViewById(R.id.et_day);
        et_phone = findViewById(R.id.et_phone);
        et_baby_weight = findViewById(R.id.et_baby_weight);
        et_baby_height = findViewById(R.id.et_baby_height);
        et_birth_count = findViewById(R.id.et_birth_count);
        et_birth_age = findViewById(R.id.et_birth_age);
        rg_birth_state = findViewById(R.id.rg_birth_state);
        rg_assisted_reproduction = findViewById(R.id.rg_assisted_reproduction);
        et_parity_count = findViewById(R.id.et_parity_count);
        rg_birth_injury = findViewById(R.id.rg_birth_injury);
        rg_stifle = findViewById(R.id.rg_stifle);
        rg_ICH = findViewById(R.id.rg_ICH);
        rg_genetic_history = findViewById(R.id.rg_genetic_history);
        rl_genetic_history = findViewById(R.id.rl_genetic_history);
        et_genetic_history = findViewById(R.id.et_genetic_history);
        rg_allergy_disease = findViewById(R.id.rg_allergy_disease);
        rl_allergy = findViewById(R.id.rl_allergy);
        et_allergy = findViewById(R.id.et_allergy);

        rl_other_disease = findViewById(R.id.rl_other_disease);
        et_other_disease = findViewById(R.id.et_other_disease);

        for (int i = 0; i < diseaseCheckBoxesId.length ; i++) {
            CheckBox checkBox = findViewById(diseaseCheckBoxesId[i]);
            checkBox.setOnCheckedChangeListener(checkBoxListener);
            diseaseCheckBoxes.add(checkBox);
        }

        et_father_name = findViewById(R.id.et_father_name);
        et_father_height = findViewById(R.id.et_father_height);
        et_father_weight = findViewById(R.id.et_father_weight);
        tv_father_BMI = findViewById(R.id.tv_father_BMI);

        et_mother_name = findViewById(R.id.et_mother_name);
        et_mother_height = findViewById(R.id.et_mother_height);
        et_mother_weight = findViewById(R.id.et_mother_weight);
        tv_mother_BMI = findViewById(R.id.tv_mother_BMI);

        initCultureBotton(father_culture,father_culture_id);
        initCultureBotton(mother_culture,mother_culture_id);
        initView();
    }

    private void initCultureBotton(List<RadioButton> list , int[] listId){
        for (int i = 0;i<listId.length ;i++){
            RadioButton button = findViewById(listId[i]);
            button.setOnCheckedChangeListener(cultureCheckListener);
            list.add(button);
        }
    }

    private void initView(){
        rl_back.setVisibility(View.GONE);
//        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);

        rl_complete.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.input_info);

        tv_calendar.setOnClickListener(listener);

        rg_allergy_disease.setOnCheckedChangeListener(radioButtonListener);
        rg_genetic_history.setOnCheckedChangeListener(radioButtonListener);

        et_father_height.setOnFocusChangeListener(focusChangeListener);
        et_father_weight.setOnFocusChangeListener(focusChangeListener);
        et_mother_height.setOnFocusChangeListener(focusChangeListener);
        et_mother_weight.setOnFocusChangeListener(focusChangeListener);
        et_mother_weight.setOnEditorActionListener(editorActionListener);
    }

    private RadioGroup.OnCheckedChangeListener radioButtonListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
            if ( R.id.rb_allergy_none == checkId ){
                rl_allergy.setVisibility(View.GONE);
            }
            if ( R.id.rb_allergy_has == checkId ){
                rl_allergy.setVisibility(View.VISIBLE);
            }

            if ( R.id.rb_genetic_none == checkId ){
                rl_genetic_history.setVisibility(View.GONE);
            }
            if ( R.id.rb_genetic_has == checkId ){
                rl_genetic_history.setVisibility(View.VISIBLE);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if ( R.id.cb_none == compoundButton.getId() ){
                if ( isChecked ){
                    for (int id:diseaseCheckBoxesId){
                        CheckBox checkBox = findViewById(id);
                        if ( id != R.id.cb_none ){
                            checkBox.setChecked(false);
                        }
                    }
                }
            } else {
                if ( isChecked ) {
                    int id = compoundButton.getId();
                    boolean isDisease = false;
                    for (int diseaseId : diseaseCheckBoxesId) {
                        if (id == diseaseId) {
                            isDisease = true;
                            break;
                        }
                    }
                    if (isDisease) {
                        CheckBox checkBox = findViewById(R.id.cb_none);
                        checkBox.setChecked(false);
                    }
                }
            }
            if ( R.id.cb_other_disease == compoundButton.getId() ){
                if ( isChecked ){
                    rl_other_disease.setVisibility(View.VISIBLE);
                } else {
                    rl_other_disease.setVisibility(View.GONE);
                }
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener cultureCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
            if ( true == isCheck ) {
                if (father_culture.contains(compoundButton)) {
                    refreshCultureButton(father_culture,compoundButton);
                } else {
                    refreshCultureButton(mother_culture,compoundButton);
                }
            }
        }
    };

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if ( !hasFocus ){
                if ( R.id.et_father_height == view.getId() || R.id.et_father_weight == view.getId() ){
                    String height = et_father_height.getText().toString().trim();
                    String weight = et_father_weight.getText().toString().trim();
                    if ( !TextUtils.isEmpty(height) && !TextUtils.isEmpty(weight) ){
                        tv_father_BMI.setText(calculateBMI(Float.parseFloat(weight),Float.parseFloat(height))+"");
                    }
                }
                if ( R.id.et_mother_height == view.getId() || R.id.et_mother_weight == view.getId() ){
                    String height = et_mother_height.getText().toString().trim();
                    String weight = et_mother_weight.getText().toString().trim();
                    if ( !TextUtils.isEmpty(height) && !TextUtils.isEmpty(weight) ){
                        tv_mother_BMI.setText(calculateBMI(Float.parseFloat(weight),Float.parseFloat(height))+"");
                    }
                }
            }
        }
    };

    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int event, KeyEvent keyEvent) {
            if ( event == EditorInfo.IME_ACTION_DONE) {
                String height = et_mother_height.getText().toString().trim();
                String weight = et_mother_weight.getText().toString().trim();
                if (!TextUtils.isEmpty(height) && !TextUtils.isEmpty(weight)) {
                    tv_mother_BMI.setText(calculateBMI(Float.parseFloat(weight), Float.parseFloat(height)) + "");
                }
            }
            return false;
        }
    };

    private void refreshCultureButton(List<RadioButton> buttons,CompoundButton except){
        for(RadioButton button:buttons){
            if ( !button.equals(except) ){
                button.setChecked(false);
            }
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_complete:
                    complete();
                    break;
                case R.id.tv_calendar:
                    showDateDialog();
                    break;
            }
        }
    };

    private float calculateBMI(float weight,float height){
        return (Math.round((weight * 10000) / (height*height)*100))/100f;
    }

    private void back(){
        finish();
    }

    //----------------------------------------数据提交-----------------------------------------------

    private RequestTask submitInfoTask;

    private void complete(){
        if ( checkData() ){
            MyDialog.showChooseDialog(this, getString(R.string.prompt_submit_base_info), R.string.prompt_bt_edit, R.string.prompt_bt_sure_submit, new MyDialog.ChooseDialogCallback() {
                @Override
                public void sure() {
                    submitInfo();
                }

                @Override
                public void cancel() {
                    //UNDO
                }
            });
        }
    }

    private void submitInfo(){
        try {
            submitInfoTask = new RequestTask.Builder(submitInfoCallBack).build();
            submitInfoTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestCallBack submitInfoCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {
            return UrlAddressList.URL_SAVE_USER_INFO;
        }

        @Override
        public Context getContext() {
            return InputInfoActivity.this;
        }

        @Override
        public Map<String, String> getContent() {
            Map<String,String> map = new HashMap<>();
            String json = GsonUtil.getInstance().getGson().toJson(inputData);
            map.put("paramStr",json);
            map.put("sesId",BaseApplication.getInstance().getUserData().getSessionId());
            return map;
        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject object =  new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                if ( 1 == res.optInt("isSaveUser") ){
                    startActivity(RecordActivity.class);
                } else {
                    //TODO ?
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String type() {
            return JSON;
        }
    };

    private InputData inputData = new InputData();
    private boolean checkData(){
        BabyInfo babyInfo = inputData.babyInfo;
        babyInfo.clearData();

        //宝宝姓名
        String babyName = et_baby_name.getText().toString().trim();
        if (TextUtils.isEmpty(babyName)){
            tv_title.setText("请输入宝宝姓名");
            return false;
        }
        babyInfo.TrueName = babyName;

        //出生日期
        String babyBirthday = tv_calendar.getText().toString().trim();
        if ( TextUtils.isEmpty(babyBirthday) ){
            tv_title.setText("请填写出生日期");
            return false;
        }
        babyInfo.Birthday = babyBirthday;

        //孕周
        String week = et_week.getText().toString().trim();
        if ( TextUtils.isEmpty(week) ){
            tv_title.setText("请填写孕周");
            return false;
        }
        babyInfo.PregnancyWeeks = week;

        String day = et_day.getText().toString().trim();
        if ( TextUtils.isEmpty(day) ){
            tv_title.setText("请填写孕周");
            return false;
        }
        babyInfo.PregnancyDays = day;

        //联系手机
        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            tv_title.setText("请填写联系手机");
            return false;
        }

        if (!FormatCheckUtil.isPhone(phone)){
            tv_title.setText("手机格式不正确");
            return false;
        }
        babyInfo.AccountMobile = phone;


        //出生体重
        String weight = et_baby_weight.getText().toString().trim();
        if (TextUtils.isEmpty(weight)){
            tv_title.setText("请填写出生体重");
            return false;
        }
        babyInfo.BornWeight = weight;

        //出生身长
        String height = et_baby_height.getText().toString().trim();
        if (TextUtils.isEmpty(height)){
            tv_title.setText("请填写出生身长");
            return false;
        }
        babyInfo.BornHeight = height;

        //胎次
        String parityCount = et_parity_count.getText().toString().trim();
        if (TextUtils.isEmpty(parityCount)){
            tv_title.setText("请填写胎次");
            return false;
        }
        babyInfo.Pregnancies = parityCount;

        //产次
        String birthCount = et_birth_count.getText().toString().trim();
        if (TextUtils.isEmpty(birthCount)){
            tv_title.setText("请填写产次");
            return false;
        }
        babyInfo.BirthTimes = birthCount;

        //母亲生育年龄
        String birthAge = et_birth_age.getText().toString().trim();
        if (TextUtils.isEmpty(birthAge)){
            tv_title.setText("请填写母亲生育年龄");
            return false;
        }
        babyInfo.MumBearAge = birthAge;

        //出生状态
        int birthState = singleChooseCheck(rb_birth_state);
        if ( UN_CHOOSE == birthState ){
            tv_title.setText("请选择出生状态");
            return false;
        }

        if ( R.id.rb_single_birth == birthState ){
            babyInfo.BornType = "1";
        } else {
            babyInfo.BornType = "2";
        }

        //分娩状态
        List<String> parturitionState = multipleChooseCheck(cb_parturition_state);
        if ( parturitionState.size() == 0 ){
            tv_title.setText("请选择分娩状态");
            return false;
        }

        for (int i=0;i<parturitionState.size();i++){
            if ( i == 0 ){
                babyInfo.DeliveryMethods = parturitionState.get(i);
            } else {
                babyInfo.DeliveryMethods += ","+parturitionState.get(i);
            }
        }

        //辅助生育
        int assistedReproduction = singleChooseCheck(rb_assisted_reproduction);
        if ( UN_CHOOSE == assistedReproduction ){
            tv_title.setText("请选择辅助生育");
            return false;
        }

        if ( R.id.rb_assisted_none == assistedReproduction ){
            babyInfo.AssistedReproductive = "0";
        } else if ( R.id.rb_artificial_insemination == assistedReproduction ) {
            babyInfo.AssistedReproductive = "1";
        } else {
            babyInfo.AssistedReproductive = "2";
        }

        //产伤
        int birthInjury = singleChooseCheck(rb_birth_injury);
        if ( UN_CHOOSE == birthInjury ){
            tv_title.setText("是否有产伤");
            return false;
        }

        if ( R.id.rb_injury_none == birthInjury ){
            babyInfo.BirthInjury = "0";
        } else {
            babyInfo.BirthInjury = "1";
        }

        //新生儿窒息
        int stifle = singleChooseCheck(rb_stifle);
        if ( UN_CHOOSE == stifle ){
            tv_title.setText("是否有新生儿窒息");
            return false;
        }

        if ( R.id.rb_istifle_none == stifle ){
            babyInfo.NeonatalAsphyxia = "0";
        } else {
            babyInfo.NeonatalAsphyxia = "1";
        }

        //颅内出血
        int ICH = singleChooseCheck(rb_ICH);
        if ( UN_CHOOSE == singleChooseCheck(rb_ICH) ){
            tv_title.setText("是否有颅内出血");
            return false;
        }

        if ( R.id.rb_ICH_none == ICH ){
            babyInfo.IntracranialHemorrhage = "0";
        } else {
            babyInfo.IntracranialHemorrhage = "1";
        }

        //家族遗传史
        int geneticHistoryId = singleChooseCheck(rb_genetic_history);
        if ( UN_CHOOSE == geneticHistoryId ){
            tv_title.setText("是否有家族遗传史");
            return false;
        }

        if ( R.id.rb_genetic_none == geneticHistoryId ){
            babyInfo.HereditaryHistory = "0";
        } else {
            babyInfo.HereditaryHistory = "1";
            if ( rl_genetic_history.getVisibility() == View.VISIBLE ) {
                babyInfo.HereditaryHistoryDesc = et_genetic_history.getText().toString().trim();
            } else {
                babyInfo.HereditaryHistoryDesc = "";
            }
        }

        //家族过敏疾病
        int allergyDisease = singleChooseCheck(rb_allergy_disease);
        if ( UN_CHOOSE == allergyDisease ){
            tv_title.setText("是否有家族过敏疾病");
            return false;
        }

        if ( R.id.rb_allergy_none == allergyDisease ){
            babyInfo.AllergicHistory = "0";
        } else {
            babyInfo.AllergicHistory = "1";
            if ( rl_allergy.getVisibility() == View.VISIBLE ){
                babyInfo.AllergicHistoryDesc = et_allergy.getText().toString().trim();
            } else {
                babyInfo.AllergicHistoryDesc = "";
            }
        }

        //既往史
        List<String> pastHistory = multipleChooseCheck(diseaseCheckBoxesId);
        if ( pastHistory.size() == 0 ){
            tv_title.setText("请选择既往史");
            return false;
        }

        babyInfo.PastHistory = pastHistory;
        if ( rl_other_disease.getVisibility() == View.VISIBLE ) {
            babyInfo.PastHistoryOther = et_other_disease.getText().toString().trim();
        } else {
            babyInfo.PastHistoryOther = "";
        }

        //父亲文化程度
        int fatherCalture = singleChooseCheck(father_culture_id);
        if ( UN_CHOOSE == fatherCalture ){
            tv_title.setText("请选择文化程度");
            return false;
        }
        RadioButton fatherCaltureButton = findViewById(fatherCalture);
        babyInfo.DadEducation = fatherCaltureButton.getText().toString().trim();

        //母亲文化程度
        int motherCalture = singleChooseCheck(mother_culture_id);
        if ( UN_CHOOSE == motherCalture ){
            tv_title.setText("请选择文化程度");
            return false;
        }
        RadioButton motherCaltureButton = findViewById(motherCalture);
        babyInfo.MumEducation = motherCaltureButton.getText().toString().trim();

        //父母信息赋值
        babyInfo.DadName = et_father_name.getText().toString().trim();
        babyInfo.DadHeight = et_father_height.getText().toString().trim();
        babyInfo.DadWeight = et_father_weight.getText().toString().trim();
        babyInfo.DadBMI = tv_father_BMI.getText().toString().trim();

        babyInfo.MumName = et_mother_name.getText().toString().trim();
        babyInfo.MumHeight = et_mother_height.getText().toString().trim();
        babyInfo.MumWeight = et_mother_weight.getText().toString().trim();
        babyInfo.MumBMI = tv_mother_BMI.getText().toString().trim();

        //宝宝性别
        if ( rg_gender.getCheckedRadioButtonId() == R.id.rb_famale ){
            babyInfo.Sex = "0";
        } else {
            babyInfo.Sex = "1";
        }

        //设置用户ID
        inputData.userId = BaseApplication.getInstance().getUserData().getUserid();
        return true;
    }

    private final static int UN_CHOOSE = -1;
    private int singleChooseCheck(int[] singleId){
        for (int i=0;i<singleId.length;i++){
            RadioButton button = findViewById(singleId[i]);
            if ( button.isChecked() ){
                return singleId[i];
            }
        }
        return UN_CHOOSE;
    }

    private List<String> multipleChooseCheck(int[] multipleId){
        List<String> list = new ArrayList<>();
        for (int i=0;i<multipleId.length;i++){
            CheckBox button = findViewById(multipleId[i]);
            if ( button.isChecked() ){
                list.add(button.getText().toString().trim());
            }
        }
        return list;
    }

    private int year;
    private int month;
    private int day;
    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();
        if ( year == 0 ) {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get((Calendar.DAY_OF_MONTH));
        }
        DatePickerDialog dp = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int iyear, int monthOfYear, int dayOfMonth) {
                year = iyear;
                month = monthOfYear;
                day = dayOfMonth;
                tv_calendar.setText(iyear+"-"+(monthOfYear+1)+"-"+dayOfMonth);
           }
       }, year, month, day);
       dp.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_BACK ){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class InputData {
        private String userId;
        private BabyInfo babyInfo;

        public InputData() {
            babyInfo = new BabyInfo();
        }
    }

    private class BabyInfo {
        //真实姓名
        private String TrueName;
        //性别 0 女 1 男
        private String Sex;
        //联系手机
        private String AccountMobile;
        //生日 format '1970-01-01 00:00:00'
        private String Birthday;
        //孕周(周)
        private String PregnancyWeeks;
        //孕周(日)
        private String PregnancyDays;
        //出生体重
        private String BornWeight;
        //出生身长
        private String BornHeight;
        //胎次
        private String Pregnancies;
        //产次
        private String BirthTimes;
        //母亲生育年龄
        private String MumBearAge;
        //出生状况 1单胎 2 多胎
        private String BornType;
        //分娩方式
        private String DeliveryMethods;
        //辅助生育 0无 1人工授精 2试管婴儿
        private String AssistedReproductive;
        //产伤  0无 1有
        private String BirthInjury;
        //新生儿窒息 0无 1有
        private String NeonatalAsphyxia;
        //颅内出血 0无 1有
        private String IntracranialHemorrhage;
        //家族遗传史 0无 1有
        private String HereditaryHistory;
        //家族遗传史内容
        private String HereditaryHistoryDesc;
        //家族过敏史 0无 1有
        private String AllergicHistory;
        //家族过敏史内容
        private String AllergicHistoryDesc;
        //既往史
        private List<String> PastHistory;
        //既往史其他内容
        private String PastHistoryOther;
        //父亲姓名
        private String DadName;
        //父亲文化程度
        private String DadEducation;
        //父亲身高
        private String DadHeight;
        //父亲体重
        private String DadWeight;
        //父亲BMI
        private String DadBMI;
        //母亲姓名
        private String MumName;
        //母亲文化程度
        private String MumEducation;
        //母亲身高
        private String MumHeight;
        //母亲体重
        private String MumWeight;
        //母亲BMI
        private String MumBMI;

        public void clearData(){
            HereditaryHistoryDesc = "";
            AllergicHistoryDesc = "";
            PastHistoryOther = "";
            DadName = "";
            DadHeight = "";
            DadWeight = "";
            MumBMI = "";
            MumName = "";
            MumHeight = "";
            MumWeight = "";
            MumBMI = "";
        }

    }

}
