package com.doumengmengandroidbady.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.response.ParentInfo;
import com.doumengmengandroidbady.util.FormulaUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class ParentInfoLayout extends LinearLayout {

    private Context context;

    private EditText et_father_name,et_father_height,et_father_weight;
    private TextView tv_father_BMI;
    private List<RadioButton> fatherButtons = new ArrayList<>();
    private int[] father_calture_id = new int[]{R.id.rb_father_primary,R.id.rb_father_junior,R.id.rb_father_senior,R.id.rb_father_university,R.id.rb_father_master,R.id.rb_father_doctor};

    private EditText et_mother_name,et_mother_height,et_mother_weight;
    private TextView tv_mother_BMI;
    private List<RadioButton> motherButtons = new ArrayList<>();
    private int[] mother_calture_id = new int[]{R.id.rb_mother_primary,R.id.rb_mother_junior,R.id.rb_mother_senior,R.id.rb_mother_university,R.id.rb_mother_master,R.id.rb_mother_doctor};

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
        this.context = context;
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

        for (int i=0;i<father_calture_id.length;i++){
            RadioButton button = findViewById(father_calture_id[i]);
            button.setOnCheckedChangeListener(checkedChangeListener);
            fatherButtons.add(button);
        }

        for (int i=0;i<mother_calture_id.length;i++){
            RadioButton button = findViewById(mother_calture_id[i]);
            button.setOnCheckedChangeListener(checkedChangeListener);
            motherButtons.add(button);
        }
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
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

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if ( !hasFocus ){
                if ( R.id.et_father_height == view.getId() || R.id.et_father_weight == view.getId() ){
                    String height = et_father_height.getText().toString().trim();
                    String weight = et_father_weight.getText().toString().trim();
                    if ( !TextUtils.isEmpty(height) && !TextUtils.isEmpty(weight) ){
                        tv_father_BMI.setText(FormulaUtil.formulaBMI(Float.parseFloat(weight),Float.parseFloat(height))+"");
                    }
                }
                if ( R.id.et_mother_height == view.getId() || R.id.et_mother_weight == view.getId() ){
                    String height = et_mother_height.getText().toString().trim();
                    String weight = et_mother_weight.getText().toString().trim();
                    if ( !TextUtils.isEmpty(height) && !TextUtils.isEmpty(weight) ){
                        tv_mother_BMI.setText(FormulaUtil.formulaBMI(Float.parseFloat(weight),Float.parseFloat(height))+"");
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
                    tv_mother_BMI.setText(FormulaUtil.formulaBMI(Float.parseFloat(weight), Float.parseFloat(height)) + "");
                }
            }
            return false;
        }
    };

    private String getCurrentCalture(List<RadioButton> radioButtons){
        for (RadioButton button:radioButtons){
            if ( button.isChecked() ){
                return button.getText().toString().trim();
            }
        }
        return null;
    }

    private void updateCalture(List<RadioButton> radioButtons, CompoundButton compoundButton){
        for (RadioButton button:radioButtons){
            if ( button.getId() != compoundButton.getId() ){
                button.setChecked(false);
            }
        }
    }

    private void initCalture(List<RadioButton> radioButtons,String selectContent){
        for (RadioButton button:radioButtons){
            if ( selectContent.equals(button.getText().toString()) ){
                button.setChecked(true);
                break;
            }
        }
    }

    private ParentInfo parentInfo;

    public boolean checkParentInfo(){
        return true;
    }

    public ParentInfo getParentInfo() {
        ParentInfo parentInfo = new ParentInfo();
        parentInfo.setDadName(et_father_name.getText().toString().trim());
        parentInfo.setDadHeight(et_father_height.getText().toString().trim());
        parentInfo.setDadWeight(et_father_weight.getText().toString().trim());
        parentInfo.setDadBMI(tv_father_BMI.getText().toString().trim());
        parentInfo.setDadEducation(getCurrentCalture(fatherButtons));

        parentInfo.setMumName(et_mother_name.getText().toString().trim());
        parentInfo.setMumHeight(et_mother_height.getText().toString().trim());
        parentInfo.setMumWeight(et_mother_weight.getText().toString().trim());
        parentInfo.setMumBMI(tv_mother_BMI.getText().toString().trim());
        parentInfo.setMumEducation(getCurrentCalture(motherButtons));
        return parentInfo;
    }

    public void setParentInfo(ParentInfo parentInfo) {
        if ( parentInfo == null ){
            return;
        }
        this.parentInfo = parentInfo;
        et_father_name.setText(parentInfo.getDadName());
        et_father_height.setText(parentInfo.getDadHeight());
        et_father_weight.setText(parentInfo.getDadWeight());
        tv_father_BMI.setText(parentInfo.getDadBMI());
        initCalture(fatherButtons,parentInfo.getDadEducation());

        et_mother_name.setText(parentInfo.getMumName());
        et_mother_height.setText(parentInfo.getMumHeight());
        et_mother_weight.setText(parentInfo.getMumWeight());
        tv_mother_BMI.setText(parentInfo.getMumBMI());
        initCalture(motherButtons,parentInfo.getMumEducation());
    }
}
