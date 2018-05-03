package com.doumengmeng.customer.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.request.entity.InputUserInfo;
import com.doumengmeng.customer.response.entity.ParentInfo;
import com.doumengmeng.customer.util.FormatCheckUtil;
import com.doumengmeng.customer.util.FormulaUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 父母信息
 * 创建日期: 2018/2/8 14:44
 */
public class ParentInfoLayout extends LinearLayout {

    public enum TYPE{
        EDITABLE_SHOW_MARK,
        EDITABLE_NO_MARK
    }

//    private Context context;

    private List<View> markList;

    private EditText et_father_name,et_father_height,et_father_weight;
    private TextView tv_father_BMI;
    private final List<CompoundButton> fatherButtons = new ArrayList<>();
    private final int[] father_culture_id = new int[]{R.id.rb_father_primary,R.id.rb_father_junior,R.id.rb_father_senior,R.id.rb_father_university,R.id.rb_father_master,R.id.rb_father_doctor};

    private EditText et_mother_name,et_mother_height,et_mother_weight;
    private TextView tv_mother_BMI;
    private final List<CompoundButton> motherButtons = new ArrayList<>();
    private final int[] mother_culture_id = new int[]{R.id.rb_mother_primary,R.id.rb_mother_junior,R.id.rb_mother_senior,R.id.rb_mother_university,R.id.rb_mother_master,R.id.rb_mother_doctor};

    public ParentInfoLayout(Context context) {
        super(context);
        initInfoView(context);
    }

    public ParentInfoLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInfoView(context);
    }

    public ParentInfoLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInfoView(context);
    }

    private void initInfoView(Context context){
//        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_parent_info,null);
        addView(view);
        findView();
    }

    private void findView(){
        et_father_name = findViewById(R.id.et_father_name);
        et_father_height = findViewById(R.id.et_father_height);
        et_father_weight = findViewById(R.id.et_father_weight);
        tv_father_BMI = findViewById(R.id.tv_father_BMI);

        et_father_height.setOnFocusChangeListener(focusChangeListener);
        et_father_weight.setOnFocusChangeListener(focusChangeListener);

        et_mother_name = findViewById(R.id.et_mother_name);
        et_mother_height = findViewById(R.id.et_mother_height);
        et_mother_weight = findViewById(R.id.et_mother_weight);
        tv_mother_BMI = findViewById(R.id.tv_mother_BMI);

        et_mother_height.setOnFocusChangeListener(focusChangeListener);
        et_mother_weight.setOnFocusChangeListener(focusChangeListener);
        et_mother_weight.setOnEditorActionListener(editorActionListener);

        for (int aFather_culture_id : father_culture_id) {
            RadioButton button = findViewById(aFather_culture_id);
            button.setOnCheckedChangeListener(checkedChangeListener);
            fatherButtons.add(button);
        }

        for (int aMother_culture_id : mother_culture_id) {
            RadioButton button = findViewById(aMother_culture_id);
            button.setOnCheckedChangeListener(checkedChangeListener);
            motherButtons.add(button);
        }

        findAllMarks();
    }

    private void findAllMarks(){
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

    private final CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
            if ( isCheck ) {
                if (fatherButtons.contains(compoundButton)) {
                    updateCalture(fatherButtons,compoundButton);
                }
                if (motherButtons.contains(compoundButton)) {
                    updateCalture(motherButtons,compoundButton);
                }
            }
        }
    };

    private final View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if ( !hasFocus ){
                if ( R.id.et_father_height == view.getId() || R.id.et_father_weight == view.getId() ){
                    calculateBMI(tv_father_BMI,et_father_weight,et_father_height);
//                    String height = et_father_height.getText().toString().trim();
//                    String weight = et_father_weight.getText().toString().trim();
//                    if ( !TextUtils.isEmpty(height) && !TextUtils.isEmpty(weight) ){
//                        tv_father_BMI.setText(String.valueOf(FormulaUtil.formulaBMI(Float.parseFloat(weight),Float.parseFloat(height))));
//                    }
                }
                if ( R.id.et_mother_height == view.getId() || R.id.et_mother_weight == view.getId() ){
                    calculateBMI(tv_mother_BMI,et_mother_weight,et_mother_height);
//                    String height = et_mother_height.getText().toString().trim();
//                    String weight = et_mother_weight.getText().toString().trim();
//                    if ( !TextUtils.isEmpty(height) && !TextUtils.isEmpty(weight) ){
//                        tv_mother_BMI.setText(String.valueOf(FormulaUtil.formulaBMI(Float.parseFloat(weight),Float.parseFloat(height))));
//                    }
                }
            }
        }
    };

    private void calculateBMI(TextView tv_bmi,TextView tv_weight,TextView tv_height){
        String height = tv_height.getText().toString().trim();
        String weight = tv_weight.getText().toString().trim();
        if ( !TextUtils.isEmpty(height) && !TextUtils.isEmpty(weight) ){
            tv_bmi.setText(String.valueOf(FormulaUtil.formulaBMI(Float.parseFloat(weight),Float.parseFloat(height))));
        }
    }

    private final TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int event, KeyEvent keyEvent) {
            if ( event == EditorInfo.IME_ACTION_DONE) {
                String height = et_mother_height.getText().toString().trim();
                String weight = et_mother_weight.getText().toString().trim();
                if (!TextUtils.isEmpty(height) && !TextUtils.isEmpty(weight)) {
                    tv_mother_BMI.setText(String.valueOf(FormulaUtil.formulaBMI(Float.parseFloat(weight), Float.parseFloat(height))));
                }
            }
            return false;
        }
    };

    private String getCurrentCalture(List<? extends CompoundButton> radioButtons){
        for (CompoundButton button:radioButtons){
            if ( button.isChecked() ){
                return button.getText().toString().trim();
            }
        }
        return null;
    }

    private void updateCalture(List<CompoundButton> radioButtons, CompoundButton compoundButton){
        for (CompoundButton button:radioButtons){
            if ( button.getId() != compoundButton.getId() ){
                button.setChecked(false);
            }
        }
    }

    private void initCalture(List<? extends CompoundButton> radioButtons,String selectContent){
        for (CompoundButton button:radioButtons){
            if ( selectContent.equals(button.getText().toString()) ){
                button.setChecked(true);
                break;
            }
        }
    }

    private String errorMessage;
    public boolean checkParentInfo(){
        String fatherName = et_father_name.getText().toString().trim();
        if ( !TextUtils.isEmpty(fatherName) ){
            if (!FormatCheckUtil.isChinese(fatherName)){
                errorMessage = "父亲姓名不是中文";
                return false;
            }
        }

        String fatherHeight = et_father_height.getText().toString().trim();
        if ( !TextUtils.isEmpty(fatherHeight) ){
            if ( 0 >= Float.parseFloat(fatherHeight) || Float.parseFloat(fatherHeight) > 250 ){
                errorMessage = "父亲身高 0~250cm";
                return false;
            }
        }

        String fatherWeight = et_father_weight.getText().toString().trim();
        if ( !TextUtils.isEmpty(fatherWeight) ){
            if ( 0 >= Float.parseFloat(fatherWeight) || Float.parseFloat(fatherWeight) > 150 ){
                errorMessage = "父亲体重 0~150kg";
                return false;
            }
        }

        int fatherCalture = singleChooseCheck(father_culture_id);
        if ( UN_CHOOSE == fatherCalture ){
            errorMessage = "请选择文化程度";
            return false;
        }

        String monthName = et_mother_name.getText().toString().trim();
        if ( !TextUtils.isEmpty(monthName) ){
            if (!FormatCheckUtil.isChinese(monthName)){
                errorMessage = "母亲姓名不是中文";
                return false;
            }
        }

        String motherHeight = et_mother_height.getText().toString().trim();
        if ( !TextUtils.isEmpty(motherHeight) ){
            if ( 0 >= Float.parseFloat(motherHeight) || Float.parseFloat(motherHeight) > 250 ){
                errorMessage = "母亲身高 0~250cm";
                return false;
            }
        }

        String motherWeight = et_mother_weight.getText().toString().trim();
        if ( !TextUtils.isEmpty(motherWeight) ){
            if ( 0 >= Float.parseFloat(motherWeight) || Float.parseFloat(motherWeight) > 150 ){
                errorMessage = "母亲体重 0~150kg";
                return false;
            }
        }

        int motherCalture = singleChooseCheck(mother_culture_id);
        if ( UN_CHOOSE == motherCalture ){
            errorMessage = "请选择文化程度";
            return false;
        }
        return true;
    }

    public String getErrorMsg(){
        return errorMessage;
    }

    private final static int UN_CHOOSE = -1;
    private int singleChooseCheck(int[] singleId){
        for (int aSingleId : singleId) {
            RadioButton button = findViewById(aSingleId);
            if (button.isChecked()) {
                return aSingleId;
            }
        }
        return UN_CHOOSE;
    }

    private final InputUserInfo.ParentInfo parentInfo = new InputUserInfo.ParentInfo();
    public InputUserInfo.ParentInfo getParentInfo() {
        parentInfo.clearData();
        parentInfo.setDadName(et_father_name.getText().toString().trim());
        parentInfo.setDadHeight(et_father_height.getText().toString().trim());
        parentInfo.setDadWeight(et_father_weight.getText().toString().trim());
        calculateBMI(tv_father_BMI,et_father_weight,et_father_height);
        parentInfo.setDadBMI(tv_father_BMI.getText().toString().trim());
        parentInfo.setDadEducation(getCurrentCalture(fatherButtons));

        parentInfo.setMumName(et_mother_name.getText().toString().trim());
        parentInfo.setMumHeight(et_mother_height.getText().toString().trim());
        parentInfo.setMumWeight(et_mother_weight.getText().toString().trim());
        calculateBMI(tv_mother_BMI,et_mother_weight,et_mother_height);
        parentInfo.setMumBMI(tv_mother_BMI.getText().toString().trim());
        parentInfo.setMumEducation(getCurrentCalture(motherButtons));
        return parentInfo;
    }

    public void setType(TYPE type){
        if ( TYPE.EDITABLE_NO_MARK == type ) {
            ParentInfo parentInfo = BaseApplication.getInstance().getParentInfo();
            if ( parentInfo != null ) {
                et_father_name.setText(parentInfo.getDadName());
                et_father_height.setText(parentInfo.getDadHeight());
                et_father_weight.setText(parentInfo.getDadWeight());
                tv_father_BMI.setText(parentInfo.getDadBMI());
                initCalture(fatherButtons, parentInfo.getDadEducation());
                et_mother_name.setText(parentInfo.getMumName());
                et_mother_height.setText(parentInfo.getMumHeight());
                et_mother_weight.setText(parentInfo.getMumWeight());
                tv_mother_BMI.setText(parentInfo.getMumBMI());
                initCalture(motherButtons, parentInfo.getMumEducation());
                hiddenMark();
            }
        }
    }

    private void hiddenMark(){
        for (View view: markList){
            view.setVisibility(View.GONE);
        }
    }

}
