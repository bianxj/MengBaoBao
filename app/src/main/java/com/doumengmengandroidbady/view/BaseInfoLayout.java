package com.doumengmengandroidbady.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.entity.BaseInfo;

/**
 * Created by Administrator on 2017/12/13.
 */

public class BaseInfoLayout extends LinearLayout {

    private Context context;
    private BaseInfo baseInfo;

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
            ,tv_phone,tv_birth_weight,tv_birth_heigh,tv_parity_count,tv_birth_count
            ,tv_birth_age;
    private EditText et_week , et_day;

    private RadioGroup rg_allergy_disease;

    private RelativeLayout rl_allergy , rl_other_disease;

    private CheckBox cb_other_disease;

    private void findView(){

        rg_allergy_disease = findViewById(R.id.rg_allergy_disease);
        rl_allergy = findViewById(R.id.rl_allergy);
        rl_other_disease = findViewById(R.id.rl_other_disease);

        cb_other_disease = findViewById(R.id.cb_other_disease);
    }

    private void initView(){
        rg_allergy_disease.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_allergy_none:
                        rl_allergy.setVisibility(View.GONE);
                        break;
                    case R.id.rb_allergy_has:
                        rl_allergy.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        cb_other_disease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ){
                    rl_other_disease.setVisibility(View.VISIBLE);
                } else {
                    rl_other_disease.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setBaseInfo(BaseInfo baseInfo){
        this.baseInfo = baseInfo;
    }

    public boolean checkBaseInfo(){
        return false;
    }

    public BaseInfo getBaseInfo(){
        return baseInfo;
    }

}
