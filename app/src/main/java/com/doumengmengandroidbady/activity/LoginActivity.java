package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/5.
 */
public class LoginActivity extends BaseActivity {

    private Button bt_back;
    private EditText et_phone,et_login_pwd;
    private TextView tv_prompt;
    private Button bt_sure;
    private TextView tv_fast_register,tv_forgot_pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        configView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( handler != null ){
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void findView(){
        bt_back = findViewById(R.id.bt_back);
        et_phone = findViewById(R.id.et_phone);
        et_login_pwd = findViewById(R.id.et_login_pwd);

        tv_prompt = findViewById(R.id.tv_prompt);
        bt_sure = findViewById(R.id.bt_sure);
        tv_fast_register = findViewById(R.id.tv_fast_register);
        tv_forgot_pwd = findViewById(R.id.tv_forgot_pwd);
    }

    private void configView(){
        bt_sure.setOnClickListener(listener);
        bt_back.setOnClickListener(listener);
        tv_fast_register.setOnClickListener(listener);
        tv_forgot_pwd.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bt_back:
                    back();
                    break;
                case R.id.bt_sure:
                    sure();
                    break;
                case R.id.tv_fast_register:
                    gotoRegister();
                    break;
                case R.id.tv_forgot_pwd:
                    gotoChangePwd();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void sure(){
        //TODO
    }

    private void gotoRegister(){
        startActivity(RegisterActivity.class);
    }

    private void gotoChangePwd(){
        startActivity(ChangePwdActivity.class);
    }

    private Handler handler = new Handler(){

    };

}
