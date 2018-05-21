package com.doumengmeng.customer.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.request.entity.InputUserInfo;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.util.EditTextUtil;
import com.doumengmeng.customer.util.FormatCheckUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 基本信息
 * 创建日期: 2018/2/8 14:44
 */
public class BaseInfoLayout extends LinearLayout {

    private final static String UN_KNOW = "未知";

    public enum TYPE{
        EDITABLE,
        EDITABLE_NET_USER,
        READ_ONLY
    }

    //未选择状态
    private final static int UN_CHOOSE = -1;

//    private Context context;

    public BaseInfoLayout(Context context) {
        super(context);
        initInfoView(context);
    }

    public BaseInfoLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInfoView(context);
    }

    public BaseInfoLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInfoView(context);
    }

    private void initInfoView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.view_base_info,null);
        addView(view);
        findView();
        initView();
    }

    private TYPE type;

    private EditText et_baby_name , et_baby_gender , et_baby_birthday
            ,et_phone,et_baby_weight, et_baby_height,et_week,et_day
            ,et_parity_count,et_birth_count,et_birth_age;

    private TextView tv_calendar;
    private TextView tv_birth_age_unit;

    private LinearLayout ll_parturition_state;
    private final List<CompoundButton> parturitionCheckBox = new ArrayList<>();
    private RadioGroup rg_genetic_history , rg_allergy_disease;
    private RelativeLayout rl_genetic_history , rl_allergy;
    private EditText et_genetic_history , et_allergy;

    private LinearLayout ll_past_history;
    private final List<CompoundButton> pastHistoryCheckBox = new ArrayList<>();
    private RelativeLayout rl_other_disease;
    private EditText et_other_disease;

    private List<CompoundButton> checkBoxList = null;
    private List<RadioButton> radioButtonList = null;
    private List<EditText> netEditList = null;
    private List<EditText> editList = null;
    private List<View> markList = null;

    private RadioGroup rg_gender;

    //单选按钮
    private final int[] rb_birth_state = new int[]{R.id.rb_single_birth,R.id.rb_spolyembryony};
    private final int[] rb_assisted_reproduction = new int[]{R.id.rb_assisted_none,R.id.rb_artificial_insemination,R.id.rb_tube_baby};
    private final int[] rb_birth_injury = new int[]{R.id.rb_injury_none,R.id.rb_injury_has};
    private final int[] rb_stifle = new int[]{R.id.rb_istifle_none,R.id.rb_istifle_has};
    private final int[] rb_ICH = new int[]{R.id.rb_ICH_none,R.id.rb_ICH_has};
    private final int[] rb_genetic_history = new int[]{R.id.rb_genetic_none,R.id.rb_genetic_has};
    private final int[] rb_allergy_disease = new int[]{R.id.rb_allergy_none,R.id.rb_allergy_has};

    private void findView(){
        et_baby_name = findViewById(R.id.et_baby_name);
        et_baby_gender = findViewById(R.id.et_baby_gender);
        et_baby_birthday = findViewById(R.id.et_baby_birthday);
        et_phone = findViewById(R.id.et_phone);
        et_baby_weight = findViewById(R.id.et_baby_weight);
        et_baby_height = findViewById(R.id.et_baby_height);
        et_week = findViewById(R.id.et_week);
        et_day = findViewById(R.id.et_day);
        et_parity_count = findViewById(R.id.et_parity_count);
        et_birth_count = findViewById(R.id.et_birth_count);
        et_birth_age = findViewById(R.id.et_birth_age);

        tv_birth_age_unit = findViewById(R.id.tv_birth_age_unit);

        tv_calendar = findViewById(R.id.tv_calendar);

        ll_parturition_state = findViewById(R.id.ll_parturition_state);

        rg_genetic_history = findViewById(R.id.rg_genetic_history);
        rg_allergy_disease = findViewById(R.id.rg_allergy_disease);
        rl_genetic_history = findViewById(R.id.rl_genetic_history);
        rl_allergy = findViewById(R.id.rl_allergy);
        et_genetic_history = findViewById(R.id.et_genetic_history);
        et_allergy = findViewById(R.id.et_allergy);

        ll_past_history = findViewById(R.id.ll_past_history);
        rl_other_disease = findViewById(R.id.rl_other_disease);
        et_other_disease = findViewById(R.id.et_other_disease);

        rg_gender = findViewById(R.id.rg_gender);

        new EditTextUtil(et_baby_height);
        new EditTextUtil(et_baby_weight);

        findEditText();
        findNetEditText();
        findAllRadioBottonList();
        findAllCheckBoxList();
        findAllMark();
        findCheckBox(parturitionCheckBox,ll_parturition_state);
        findCheckBox(pastHistoryCheckBox,ll_past_history);

        initView();
    }

    private void findNetEditText(){
        netEditList = new ArrayList<>();
        netEditList.add(et_baby_name);
        netEditList.add(et_baby_gender);
        netEditList.add(et_baby_birthday);
        netEditList.add(et_phone);
        netEditList.add(et_baby_weight);
        netEditList.add(et_baby_height);
        netEditList.add(et_week);
        netEditList.add(et_day);
    }

    private void findEditText(){
        editList = new ArrayList<>();
        editList.add(et_baby_name);
        editList.add(et_baby_gender);
        editList.add(et_baby_birthday);
        editList.add(et_phone);
        editList.add(et_baby_weight);
        editList.add(et_baby_height);
        editList.add(et_week);
        editList.add(et_day);
        editList.add(et_parity_count);
        editList.add(et_birth_count);
        editList.add(et_birth_age);
        editList.add(et_allergy);
        editList.add(et_genetic_history);
        editList.add(et_other_disease);
    }

    private void findAllCheckBoxList(){
        checkBoxList = new ArrayList<>();
        findCheckBox(checkBoxList,this);
    }

    private void findAllRadioBottonList(){
        radioButtonList = new ArrayList<>();
        findRadioButton(radioButtonList,this);
    }

    private void findRadioButton(List<RadioButton> radioButtons,ViewGroup group){
        for (int i=0;i<group.getChildCount();i++){
            View view = group.getChildAt(i);
            if ( view instanceof ViewGroup ){
                findRadioButton(radioButtons, (ViewGroup) view);
            } else if ( view instanceof RadioButton ){
                radioButtons.add((RadioButton) view);
            }
        }
    }

    private void findAllMark(){
        markList = new ArrayList<>();
        findMark(markList,this);
    }

    private void findMark(List<View> marks,ViewGroup group){
        for (int i=0;i<group.getChildCount();i++){
            View view = group.getChildAt(i);
            if ( "mark".equals(view.getTag()) ){
                marks.add(view);
            }
            if ( view instanceof ViewGroup ){
                findMark(marks, (ViewGroup) view);
            }
        }
    }

    private void findCheckBox(List<CompoundButton> list , ViewGroup group){
        for (int i=0;i<group.getChildCount();i++){
            View view = group.getChildAt(i);
            if ( view instanceof ViewGroup ){
                findCheckBox(list, (ViewGroup) view);
            } else if ( view instanceof CheckBox ){
                list.add((CheckBox) view);
            }
        }
    }

    private void initView(){
        tv_calendar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        rg_allergy_disease.setOnCheckedChangeListener(radioGroupChangeListener);
        rg_genetic_history.setOnCheckedChangeListener(radioGroupChangeListener);

        for (CompoundButton checkBox:pastHistoryCheckBox){
            checkBox.setOnCheckedChangeListener(checkBoxChangeListener);
        }
    }

    private final RadioGroup.OnCheckedChangeListener radioGroupChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if ( checkedId == R.id.rb_genetic_none ){
                rl_genetic_history.setVisibility(View.GONE);
            }
            if ( checkedId == R.id.rb_genetic_has ) {
                rl_genetic_history.setVisibility(View.VISIBLE);
            }
            if ( checkedId == R.id.rb_allergy_none ){
                rl_allergy.setVisibility(View.GONE);
            }
            if ( checkedId == R.id.rb_allergy_has ){
                rl_allergy.setVisibility(View.VISIBLE);
            }
        }
    };

    private final CompoundButton.OnCheckedChangeListener checkBoxChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if ( R.id.cb_none == compoundButton.getId() ){
                if ( isChecked ){
                    for (CompoundButton checkBox:pastHistoryCheckBox){
                        if ( !checkBox.equals(compoundButton) ){
                            checkBox.setChecked(false);
                        }
                    }
                }
            } else {
                if ( isChecked ) {
                    if ( pastHistoryCheckBox.contains(compoundButton) ){
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

    public void setType(BaseInfoLayout.TYPE type){
        this.type = type;
        if ( type == TYPE.EDITABLE ){
            initEditableArea();
        } else if ( type == TYPE.EDITABLE_NET_USER ) {
            initData();
            initEditableNetUserArea();
        } else {
            initData();
            initReadableArea();
        }
    }

    private void initEditableArea(){
        et_baby_birthday.setVisibility(View.GONE);
        et_baby_gender.setVisibility(View.GONE);
    }

    private void initEditableNetUserArea(){
        rg_gender.setVisibility(View.GONE);
        tv_calendar.setVisibility(View.GONE);
        for (EditText editText:netEditList){
            editText.setEnabled(false);
            editText.setBackground(null);
            if ( editText != et_week
                    && editText != et_day ) {
                editText.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            }
        }
        hiddenMark();
    }

    private void initReadableArea(){
        findViewById(R.id.cb_other_disease).setVisibility(View.GONE);

        rg_gender.setVisibility(View.GONE);
        tv_calendar.setVisibility(View.GONE);
        for (EditText editText:editList){
            editText.setEnabled(false);
            if ( editText != et_allergy
                    && editText != et_genetic_history
                    && editText != et_other_disease){
                editText.setBackground(null);
                if ( editText != et_week
                        && editText != et_day ) {
                    editText.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                }
            }
        }
        for (CompoundButton checkBox:checkBoxList){
            checkBox.setEnabled(false);
        }
        for (RadioButton button:radioButtonList){
            button.setEnabled(false);
        }
        hiddenMark();
    }

    private void hiddenMark(){
        for (View view:markList){
            view.setVisibility(View.GONE);
        }
    }

    private void initData(){
        UserData userData = BaseApplication.getInstance().getUserData();
        et_baby_name.setText(userData.getTruename());
        et_baby_gender.setText("1".equals(userData.getSex())?"男":"女");
        et_baby_birthday.setText(userData.getBirthday());
        et_week.setText(userData.getPregnancyweeks());
        et_day.setText(userData.getPregnancydays());
        et_phone.setText(userData.getAccountmobile());
        et_baby_height.setText(userData.getBornheight());
        et_baby_weight.setText(userData.getBornweight());

        et_birth_count.setText(userData.getBirthtimes());
        et_parity_count.setText(userData.getPregnancies());
        if ( type == TYPE.READ_ONLY && (TextUtils.isEmpty(userData.getMumbearage()) || Integer.parseInt(userData.getMumbearage()) == 0) ){
            et_birth_age.setText(UN_KNOW);
            tv_birth_age_unit.setVisibility(View.INVISIBLE);
        } else {
            et_birth_age.setText(userData.getMumbearage());
            tv_birth_age_unit.setVisibility(View.VISIBLE);
        }

        if ( "1".equals(userData.getBorntype()) ){
            selectRadioButton(R.id.rb_single_birth);
        } else {
            selectRadioButton(R.id.rb_spolyembryony);
        }

        List<String> string = userData.getDeliverymethods();
        selectCheckBox(parturitionCheckBox,string);

        String assistedreproductive = userData.getAssistedreproductive();
        if ( "0".equals(assistedreproductive) ){
            selectRadioButton(R.id.rb_assisted_none);
        } else if ( "1".equals(assistedreproductive) ){
            selectRadioButton(R.id.rb_artificial_insemination);
        } else {
            selectRadioButton(R.id.rb_tube_baby);
        }

        selectRadioButton(userData.getBirthinjury(),R.id.rb_injury_none,R.id.rb_injury_has);
//        selectRadioButton(userData.getNeonatalasphyxia(),R.id.rb_istifle_none,R.id.rb_istifle_has);
//        selectRadioButton(userData.getIntracranialhemorrhage(),R.id.rb_ICH_none,R.id.rb_ICH_has);
        selectRadioButton(userData.getHereditaryhistory(),R.id.rb_genetic_none,R.id.rb_genetic_has);
        selectRadioButton(userData.getAllergichistory(),R.id.rb_allergy_none,R.id.rb_allergy_has);

        selectCheckBox(pastHistoryCheckBox,userData.getPasthistory());

        et_allergy.setText(userData.getAllergichistorydesc());
        et_genetic_history.setText(userData.getHereditaryhistorydesc());
        et_other_disease.setText(userData.getPasthistoryother());
    }

    private void selectRadioButton(String value, int none, int has){
        if ( "0".equals(value) ){
            selectRadioButton(none);
        } else {
            selectRadioButton(has);
        }
    }

    private void selectCheckBox(List<CompoundButton> checkBoxes , List<String> value ){
        if ( value == null || value.size() == 0 ){
            return;
        }
        for (int i=0;i<checkBoxes.size();i++){
            CompoundButton checkBox = checkBoxes.get(i);
            if ( value.contains(checkBox.getText().toString().trim()) ){
                checkBox.setChecked(true);
            }
        }
    }

    private void selectRadioButton(int id){
        RadioButton button = findViewById(id);
        button.setChecked(true);
    }

    private String errorMessage;
    public boolean checkBaseInfo(){
        if ( type == TYPE.READ_ONLY ){
            return true;
        } else if ( type == TYPE.EDITABLE_NET_USER ){
            return checkEditableNetType();
        } else {
            return checkEditableType();
        }
    }

    private boolean checkEditableNetType(){
        //产次
        String birthCount = et_birth_count.getText().toString().trim();
        if (TextUtils.isEmpty(birthCount)){
            errorMessage = "请填写产次";
            return false;
        }

        if ( 0 > Integer.parseInt(birthCount) || Integer.parseInt(birthCount) > 9 ){
            errorMessage = "产次 0~9";
            return false;
        }

        //胎次
        String parityCount = et_parity_count.getText().toString().trim();
        if (TextUtils.isEmpty(parityCount)){
            errorMessage = "请填写胎次";
            return false;
        }

        if ( 0 > Integer.parseInt(parityCount) || Integer.parseInt(parityCount) > 9 ){
            errorMessage = "胎次 0~9";
            return false;
        }

        //母亲生育年龄
        String birthAge = et_birth_age.getText().toString().trim();
        if (TextUtils.isEmpty(birthAge)){
            errorMessage = "请填写母亲生育年龄";
            return false;
        }

        if ( 0 >= Integer.parseInt(birthAge) || Integer.parseInt(birthAge) > 99 ){
            errorMessage = "母亲生育年龄 0~99";
            return false;
        }

        //出生状态
        int birthState = singleChooseCheck(rb_birth_state);
        if ( UN_CHOOSE == birthState ){
            errorMessage = "请选择出生状态";
            return false;
        }

        //分娩状态
        if ( !isCheckBoxChoose(parturitionCheckBox) ){
            errorMessage = "请选择分娩状态";
            return false;
        }

        //辅助生育
        int assistedReproduction = singleChooseCheck(rb_assisted_reproduction);
        if ( UN_CHOOSE == assistedReproduction ){
            errorMessage = "请选择辅助生育";
            return false;
        }

        //产伤
        int birthInjury = singleChooseCheck(rb_birth_injury);
        if ( UN_CHOOSE == birthInjury ){
            errorMessage = "是否有产伤";
            return false;
        }

//        //新生儿窒息
//        int stifle = singleChooseCheck(rb_stifle);
//        if ( UN_CHOOSE == stifle ){
//            errorMessage = "是否有新生儿窒息";
//            return false;
//        }
//
//        //颅内出血
//        int ICH = singleChooseCheck(rb_ICH);
//        if ( UN_CHOOSE == singleChooseCheck(rb_ICH) ){
//            errorMessage = "是否有颅内出血";
//            return false;
//        }

        //家族遗传史
        int geneticHistoryId = singleChooseCheck(rb_genetic_history);
        if ( UN_CHOOSE == geneticHistoryId ){
            errorMessage = "是否有家族遗传史";
            return false;
        }

        //家族过敏疾病
        int allergyDisease = singleChooseCheck(rb_allergy_disease);
        if ( UN_CHOOSE == allergyDisease ){
            errorMessage = "是否有家族过敏疾病";
            return false;
        }

        //既往史
        if ( !isCheckBoxChoose(pastHistoryCheckBox) ){
            errorMessage = "请选择既往史";
            return false;
        }
        return true;
    }

    private boolean checkEditableType(){
        String babyName = et_baby_name.getText().toString().trim();
        if (TextUtils.isEmpty(babyName)){
            errorMessage = "请输入宝宝姓名";
            return false;
        }

        if ( !FormatCheckUtil.isChinese(babyName) ){
            errorMessage = "宝宝姓名不是中文";
            return false;
        }

        //出生日期
        String babyBirthday = tv_calendar.getText().toString().trim();
        if ( TextUtils.isEmpty(babyBirthday) ){
            errorMessage = "请填写出生日期";
            return false;
        }

        //孕周
        String week = et_week.getText().toString().trim();
        if ( TextUtils.isEmpty(week) ){
            errorMessage = "请填写孕周";
            return false;
        }

        if ( 20 > Integer.parseInt(week) || Integer.parseInt(week) > 60 ){
            errorMessage = "孕周(周) 20~60";
            return false;
        }

        String day = et_day.getText().toString().trim();
        if ( TextUtils.isEmpty(day) ){
            errorMessage = "请填写孕周";
            return false;
        }

        if ( 0 > Integer.parseInt(day) || Integer.parseInt(day) > 7 ){
            errorMessage = "孕周(天) 0~7";
            return false;
        }

//        //联系手机
//        String phone = et_phone.getText().toString().trim();
//        if (TextUtils.isEmpty(phone)){
//            errorMessage = "请填写联系手机";
//            return false;
//        }
//
//        if (!FormatCheckUtil.isPhone(phone)){
//            errorMessage = "手机格式不正确";
//            return false;
//        }


        //出生体重
        String weight = et_baby_weight.getText().toString().trim();
        if (TextUtils.isEmpty(weight)){
            errorMessage = "请填写出生体重";
            return false;
        }

        if ( 0 >= Float.parseFloat(weight) || Float.parseFloat(weight) > 150 ){
            errorMessage = "出生体重 0~150kg";
            return false;
        }

        //出生身长
        String height = et_baby_height.getText().toString().trim();
        if (TextUtils.isEmpty(height)){
            errorMessage = "请填写出生身长";
            return false;
        }

        if ( 0 >= Float.parseFloat(height) || Float.parseFloat(height) > 250 ){
            errorMessage = "出生身高 0~250cm";
            return false;
        }

        return checkEditableNetType();
    }

    private int singleChooseCheck(int[] singleId){
        for (int aSingleId : singleId) {
            RadioButton button = findViewById(aSingleId);
            if (button.isChecked()) {
                return aSingleId;
            }
        }
        return UN_CHOOSE;
    }

    private boolean isCheckBoxChoose(List<CompoundButton> checkBoxes){
        for (CompoundButton checkBox:checkBoxes){
            if ( checkBox.isChecked() ){
                return true;
            }
        }
        return false;
    }

    private List<String> multipleChooseCheck(List<CompoundButton> checkBoxes){
        List<String> list = new ArrayList<>();
        for (int i=0;i<checkBoxes.size();i++){
            CompoundButton button = checkBoxes.get(i);
            if ( button.isChecked() ){
                list.add(button.getText().toString().trim());
            }
        }
        return list;
    }

    public String getCheckErrorMsg(){
        return errorMessage;
    }

    private final InputUserInfo.BabyInfo babyInfo = new InputUserInfo.BabyInfo();
    public InputUserInfo.BabyInfo getBabyInfo(){
        babyInfo.clearData();
        if ( type == TYPE.EDITABLE ) {
            babyInfo.setBirthday(getValueFromEditText(tv_calendar));
            if ( rg_gender.getCheckedRadioButtonId() == R.id.rb_famale ){
                babyInfo.setSex("0");
            } else {
                babyInfo.setSex("1");
            }
        } else {
            babyInfo.setBirthday(getValueFromEditText(et_baby_birthday));
            if ( "女".equals(getValueFromEditText(et_baby_gender)) ){
                babyInfo.setSex("0");
            } else {
                babyInfo.setSex("1");
            }
        }
        babyInfo.setTrueName(getValueFromEditText(et_baby_name));
        babyInfo.setPregnancyWeeks(getValueFromEditText(et_week));
        babyInfo.setPregnancyDays(getValueFromEditText(et_day));
//        babyInfo.setAccountMobile(getValueFromEditText(et_phone));
        babyInfo.setAccountMobile(BaseApplication.getInstance().getUserData().getAccountmobile());
        babyInfo.setBornWeight(getValueFromEditText(et_baby_weight));
        babyInfo.setBornHeight(getValueFromEditText(et_baby_height));
        babyInfo.setPregnancies(getValueFromEditText(et_parity_count));
        babyInfo.setBirthTimes(getValueFromEditText(et_birth_count));
        if ( !et_birth_age.isEnabled() && UN_KNOW.equals(et_birth_age.getText().toString().trim()) ){
            babyInfo.setMumBearAge("0");
        } else {
            babyInfo.setMumBearAge(getValueFromEditText(et_birth_age));
        }

        int birthState = singleChooseCheck(rb_birth_state);
        if ( R.id.rb_single_birth == birthState ){
            babyInfo.setBornType("1");
        } else {
            babyInfo.setBornType("2");
        }

        List<String> parturitionState = multipleChooseCheck(parturitionCheckBox);
        babyInfo.setDeliveryMethods(parturitionState);

        int assistedReproduction = singleChooseCheck(rb_assisted_reproduction);
        if ( R.id.rb_assisted_none == assistedReproduction ){
            babyInfo.setAssistedReproductive("0");
        } else if ( R.id.rb_artificial_insemination == assistedReproduction ) {
            babyInfo.setAssistedReproductive("1");
        } else {
            babyInfo.setAssistedReproductive("2");
        }

        int birthInjury = singleChooseCheck(rb_birth_injury);
        if ( R.id.rb_injury_none == birthInjury ){
            babyInfo.setBirthInjury("0");
        } else {
            babyInfo.setBirthInjury("1");
        }

//        int stifle = singleChooseCheck(rb_stifle);
//        if ( R.id.rb_istifle_none == stifle ){
//            babyInfo.setNeonatalAsphyxia("0");
//        } else {
//            babyInfo.setNeonatalAsphyxia("1");
//        }
//
//        int ICH = singleChooseCheck(rb_ICH);
//        if ( R.id.rb_ICH_none == ICH ){
//            babyInfo.setIntracranialHemorrhage("0");
//        } else {
//            babyInfo.setIntracranialHemorrhage("1");
//        }

        int geneticHistoryId = singleChooseCheck(rb_genetic_history);
        if ( R.id.rb_genetic_none == geneticHistoryId ){
            babyInfo.setHereditaryHistory("0");
        } else {
            babyInfo.setHereditaryHistory("1");
            if ( rl_genetic_history.getVisibility() == View.VISIBLE ) {
                babyInfo.setHereditaryHistoryDesc(getValueFromEditText(et_genetic_history));
            }
        }

        int allergyDisease = singleChooseCheck(rb_allergy_disease);
        if ( R.id.rb_allergy_none == allergyDisease ){
            babyInfo.setAllergicHistory("0");
        } else {
            babyInfo.setAllergicHistory("1");
            if ( rl_allergy.getVisibility() == View.VISIBLE ){
                babyInfo.setAllergicHistoryDesc(getValueFromEditText(et_allergy));
            }
        }

        List<String> pastHistory = multipleChooseCheck(pastHistoryCheckBox);
        babyInfo.setPastHistory(pastHistory);
        if ( rl_other_disease.getVisibility() == View.VISIBLE ) {
            babyInfo.setPastHistoryOther(getValueFromEditText(et_other_disease));
        }
        return babyInfo;
    }

    private String getValueFromEditText(TextView editText){
        return editText.getText().toString().trim();
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
        DatePickerDialog dp = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int iyear, int monthOfYear, int dayOfMonth) {
                year = iyear;
                month = monthOfYear;
                day = dayOfMonth;

                tv_calendar.setText(String.format(getContext().getString(R.string.dialog_year_month_day),year,month+1,day));
            }
        }, year, month, day);

        Calendar last = Calendar.getInstance();
        last.set(last.get(Calendar.YEAR)-3,last.get(Calendar.MONTH),calendar.get((Calendar.DATE)));
        dp.getDatePicker().setMinDate(last.getTime().getTime());
        dp.getDatePicker().setMaxDate(calendar.getTime().getTime());
        dp.show();
    }

}
