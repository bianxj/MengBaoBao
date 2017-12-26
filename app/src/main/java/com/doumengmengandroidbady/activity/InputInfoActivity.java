package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.util.MyDialog;

import java.util.ArrayList;
import java.util.List;

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
    private RadioGroup rg_parturition_state;
    private RadioGroup rg_assisted_reproduction;
    private EditText et_parity_count;
    private RadioGroup rg_birth_injury;
    private RadioGroup rg_stifle;
    private RadioGroup rg_ICH;
    private RadioGroup rg_genetic_history;
    private RadioGroup rg_allergy_disease;

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
            R.id.cb_ICH,
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
    private RadioGroup rg_father_calture;
    private EditText et_father_height;
    private EditText et_father_weight;
    private EditText et_mother_name;
    private RadioGroup rg_mother_calture;
    private EditText et_mother_height;
    private EditText et_mother_weight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_info);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
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
        rg_parturition_state = findViewById(R.id.rg_parturition_state);
        rg_assisted_reproduction = findViewById(R.id.rg_assisted_reproduction);
        et_parity_count = findViewById(R.id.et_parity_count);
        rg_birth_injury = findViewById(R.id.rg_birth_injury);
        rg_stifle = findViewById(R.id.rg_stifle);
        rg_ICH = findViewById(R.id.rg_ICH);
        rg_genetic_history = findViewById(R.id.rg_genetic_history);
        rg_allergy_disease = findViewById(R.id.rg_allergy_disease);

        et_other_disease = findViewById(R.id.et_other_disease);

        for (int i = 0; i < diseaseCheckBoxesId.length ; i++) {
            CheckBox checkBox = findViewById(diseaseCheckBoxesId[i]);
            checkBox.setOnCheckedChangeListener(diseaseCheckListener);
            diseaseCheckBoxes.add(checkBox);
        }

        et_father_name = findViewById(R.id.et_father_name);
        rg_father_calture = findViewById(R.id.rg_father_calture);
        et_father_height = findViewById(R.id.et_father_height);
        et_father_weight = findViewById(R.id.et_father_weight);

        et_mother_name = findViewById(R.id.et_mother_name);
        rg_mother_calture = findViewById(R.id.rg_mother_calture);
        et_mother_height = findViewById(R.id.et_mother_height);
        et_mother_weight = findViewById(R.id.et_mother_weight);

        initView();
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);

        rl_complete.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.input_info);
    }

    private CompoundButton.OnCheckedChangeListener diseaseCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        }
    };

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
            }
        }
    };

    private void back(){
        finish();
    }

    private void complete(){
        if ( checkData() ){
            MyDialog.showChooseDialog(this, getString(R.string.prompt_submit_base_info), R.string.prompt_bt_edit, R.string.prompt_bt_sure_submit, new MyDialog.ChooseDialogCallback() {
                @Override
                public void sure() {
                    startActivity(RecordActivity.class);
                }

                @Override
                public void cancel() {
                    //UNDO
                }
            });
        }
    }

    private boolean checkData(){
        return true;
    }

}
