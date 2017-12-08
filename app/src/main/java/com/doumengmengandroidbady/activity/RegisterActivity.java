package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/5.
 */
public class RegisterActivity extends BaseActivity {

    private Button bt_back,bt_sure;
    private TextView bt_get_vc , tv_prompt;
    private EditText et_phone , et_vc , et_login_pwd;
    private LinearLayout ll_agreement;
    private CheckBox cb_agreement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findView();
        configView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( handler != null ) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void findView(){
        bt_back = findViewById(R.id.bt_back);
        bt_get_vc = findViewById(R.id.bt_get_vc);

        et_login_pwd = findViewById(R.id.et_login_pwd);
        et_phone = findViewById(R.id.et_phone);
        et_vc = findViewById(R.id.et_vc);

        tv_prompt = findViewById(R.id.tv_prompt);
        bt_sure = findViewById(R.id.bt_sure);

        ll_agreement = findViewById(R.id.ll_agreement);
        cb_agreement = findViewById(R.id.cb_agreement);
    }

    private void configView(){
        bt_back.setOnClickListener(listener);
        bt_get_vc.setOnClickListener(listener);
        bt_sure.setOnClickListener(listener);
        ll_agreement.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bt_back:
                    back();
                    break;
                case R.id.bt_get_vc:
                    getVc();
                    break;
                case R.id.bt_sure:
                    sure();
                    break;
                case R.id.ll_agreement:
                    clickAgreement();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void getVc(){
        //TODO
    }

    private void sure(){
        //TODO
    }

    private void clickAgreement(){
        cb_agreement.setChecked(!cb_agreement.isChecked());
    }

    private Handler handler = new Handler(){

    };

}
