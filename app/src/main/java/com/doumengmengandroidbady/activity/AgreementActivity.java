package com.doumengmengandroidbady.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/7.
 */
public class AgreementActivity extends BaseActivity {

    public static final String HIDE_BOTTOM = "hide_bottom";

    private RelativeLayout rl_bottom;
    private TextView tv_title;
    private Button bt_back;
    private LinearLayout ll_agreement;
    private Button bt_agree;
    private CheckBox cb_agreement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        findView();
        configView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findView(){
        rl_bottom = findViewById(R.id.rl_bottom);
        tv_title = findViewById(R.id.tv_title);
        bt_back = findViewById(R.id.bt_back);
        ll_agreement = findViewById(R.id.ll_agreement);
        bt_agree = findViewById(R.id.bt_agree);
        cb_agreement = findViewById(R.id.cb_agreement);
    }

    private void configView(){
        Intent intent = getIntent();
        if ( null != intent && intent.getBooleanExtra(HIDE_BOTTOM,false) ){
            rl_bottom.setVisibility(View.GONE);
            bt_agree.setOnClickListener(listener);
            ll_agreement.setOnClickListener(listener);
        }
        tv_title.setText(R.string.agreement_name);
        bt_back.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_back:
                    break;
                case R.id.ll_agreement:
                    clickAgreement();
                    break;
                case R.id.bt_agree:
                    agree();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void clickAgreement(){
        cb_agreement.setChecked(!cb_agreement.isChecked());
    }

    private void agree(){

    }

}
