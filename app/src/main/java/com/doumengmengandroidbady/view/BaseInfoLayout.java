package com.doumengmengandroidbady.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.response.UserData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class BaseInfoLayout extends LinearLayout {

    private Context context;

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
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_base_info,null);
        addView(view);
        findView();
        initView();
    }

    private TextView tv_baby_name , tv_baby_gender , tv_baby_birthday
            ,tv_phone,tv_birth_weight, tv_birth_height,tv_week,tv_day;
    private EditText et_parity_count,et_birth_count,et_birth_age;

    private LinearLayout ll_parturition_state;
    private List<CheckBox> parturitionCheckBox = new ArrayList<>();
    private RadioGroup rg_genetic_history , rg_allergy_disease;
    private RelativeLayout rl_genetic_history , rl_allergy;
    private EditText et_genetic_history , et_allergy;

    private LinearLayout ll_past_history;
    private List<CheckBox> pastHistoryCheckBox = new ArrayList<>();
    private RelativeLayout rl_other_disease;
    private EditText et_other_disease;

    private void findView(){
        tv_baby_name = findViewById(R.id.tv_baby_name);
        tv_baby_gender = findViewById(R.id.tv_baby_gender);
        tv_baby_birthday = findViewById(R.id.tv_baby_birthday);
        tv_phone = findViewById(R.id.tv_phone);
        tv_birth_weight = findViewById(R.id.tv_birth_weight);
        tv_birth_height = findViewById(R.id.tv_birth_height);
        tv_week = findViewById(R.id.tv_week);
        tv_day = findViewById(R.id.tv_day);

        et_parity_count = findViewById(R.id.et_parity_count);
        et_birth_count = findViewById(R.id.et_birth_count);
        et_birth_age = findViewById(R.id.et_birth_age);

        ll_parturition_state = findViewById(R.id.ll_parturition_state);
        findCheckBox(parturitionCheckBox,ll_parturition_state);

        rg_genetic_history = findViewById(R.id.rg_genetic_history);
        rg_allergy_disease = findViewById(R.id.rg_allergy_disease);
        rl_genetic_history = findViewById(R.id.rl_genetic_history);
        rl_allergy = findViewById(R.id.rl_allergy);
        et_genetic_history = findViewById(R.id.et_genetic_history);
        et_allergy = findViewById(R.id.et_allergy);

        ll_past_history = findViewById(R.id.ll_past_history);
        findCheckBox(pastHistoryCheckBox,ll_past_history);
        rl_other_disease = findViewById(R.id.rl_other_disease);
        et_other_disease = findViewById(R.id.et_other_disease);

        initView();
    }

    private void findCheckBox(List<CheckBox> list , ViewGroup group){
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
        rg_allergy_disease.setOnCheckedChangeListener(radioGroupChangeListener);
        rg_genetic_history.setOnCheckedChangeListener(radioGroupChangeListener);

        for (CheckBox checkBox:pastHistoryCheckBox){
            checkBox.setOnCheckedChangeListener(checkBoxChangeListener);
        }
    }

    private RadioGroup.OnCheckedChangeListener radioGroupChangeListener = new RadioGroup.OnCheckedChangeListener() {
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

    private CompoundButton.OnCheckedChangeListener checkBoxChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if ( R.id.cb_none == compoundButton.getId() ){
                if ( isChecked ){
                    for (CheckBox checkBox:pastHistoryCheckBox){
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

    public void setUserData(UserData userData){
        tv_baby_name.setText(userData.getTruename());
        tv_baby_gender.setText("1".equals(userData.getSex())?"男":"女");
        tv_baby_birthday.setText(userData.getBirthday());
        tv_week.setText(userData.getWeekdvalue());
        tv_day.setText(userData.getDaydvalue());
        tv_phone.setText(userData.getAccountmobile());
        tv_birth_height.setText(userData.getBornheight());
        tv_birth_weight.setText(userData.getBornweight());

        et_birth_count.setText(userData.getBirthtimes());
        et_parity_count.setText(userData.getPregnancies());
        et_birth_age.setText(userData.getMumbearage());

        if ( "1".equals(userData.getBorntype()) ){
            selectRadioButton(R.id.rb_single_birth);
        } else {
            selectRadioButton(R.id.rb_spolyembryony);
        }

        String string = userData.getDeliverymethods();
        String[] strings = string.split(",");
        selectCheckBox(parturitionCheckBox,Arrays.asList(strings));

        String assistedreproductive = userData.getAssistedreproductive();
        if ( "0".equals(assistedreproductive) ){
            selectRadioButton(R.id.rb_assisted_none);
        } else if ( "1".equals(assistedreproductive) ){
            selectRadioButton(R.id.rb_artificial_insemination);
        } else {
            selectRadioButton(R.id.rb_tube_baby);
        }

        selectRadioButton(userData.getBirthinjury(),R.id.rb_injury_none,R.id.rb_injury_has);
        selectRadioButton(userData.getNeonatalasphyxia(),R.id.rb_stifle_none,R.id.rb_stifle_has);
        selectRadioButton(userData.getIntracranialhemorrhage(),R.id.rb_ICH_none,R.id.rb_ICH_has);
        selectRadioButton(userData.getHereditaryhistory(),R.id.rb_genetic_none,R.id.rb_genetic_has);
        selectRadioButton(userData.getAllergichistory(),R.id.rb_allergy_none,R.id.rb_allergy_has);

        selectCheckBox(pastHistoryCheckBox,userData.getPasthistory());

        et_allergy.setText(userData.getAllergichistorydesc());
        et_genetic_history.setText(userData.getHereditaryhistorydesc());
        et_other_disease.setText(userData.getPasthistoryother());
    }

    public void selectRadioButton(String value , int none , int has){
        if ( "0".equals(value) ){
            selectRadioButton(none);
        } else {
            selectRadioButton(has);
        }
    }

    private void selectCheckBox(List<CheckBox> checkBoxes , List<String> value ){
        for (int i=0;i<checkBoxes.size();i++){
            CheckBox checkBox = checkBoxes.get(i);
            if ( value.contains(checkBox.getText().toString().trim()) ){
                checkBox.setChecked(true);
            }
        }
    }

    private void selectRadioButton(int id){
        RadioButton button = findViewById(id);
        button.setChecked(true);
    }

    public boolean checkBaseInfo(){
        //TODO
        return false;
    }

    public UserData getUserData(){
        UserData userData = new UserData();
        //TODO
        return userData;
    }

}
