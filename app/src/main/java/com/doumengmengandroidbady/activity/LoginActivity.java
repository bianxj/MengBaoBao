package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.config.Config;
import com.doumengmengandroidbady.request.ResponseErrorCode;
import com.doumengmengandroidbady.request.task.LoginTask;
import com.doumengmengandroidbady.util.FormatCheckUtil;

/**
 * 作者: 边贤君
 * 描述: 登录页面
 * 创建日期: 2018/1/8 9:50
 */
public class LoginActivity extends BaseActivity {

    private static boolean isTest = Config.isTest;

    private RelativeLayout rl_back;
    private EditText et_phone,et_login_pwd;
    private TextView tv_prompt;
    private Button bt_sure;
    private TextView tv_fast_register,tv_forgot_pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( loginTask != null ) {
            stopTask(loginTask.getTask());
        }
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        et_phone = findViewById(R.id.et_phone);
        et_login_pwd = findViewById(R.id.et_login_pwd);

        tv_prompt = findViewById(R.id.tv_prompt);
        bt_sure = findViewById(R.id.bt_sure);
        tv_fast_register = findViewById(R.id.tv_fast_register);
        tv_forgot_pwd = findViewById(R.id.tv_forgot_pwd);
    }

    private void initView(){
        bt_sure.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);
        tv_fast_register.setOnClickListener(listener);
        tv_forgot_pwd.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.bt_sure:
                    login();
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

    private LoginTask loginTask = null;
    /**
     * 作者: 边贤君
     * 描述: 登录
     * 日期: 2018/1/8 9:51
     */
    private void login(){
        if ( checkLogin() ){
            String accountMobile = et_phone.getText().toString().trim();
            String loginPwd = et_login_pwd.getText().toString().trim();
            try {
                loginTask = new LoginTask(this, accountMobile, loginPwd, new LoginTask.LoginCallBack() {
                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void onError(String result) {
                        tv_prompt.setText(ResponseErrorCode.getErrorMsg(result));
                    }

                    @Override
                    public void onPostExecute(String result) {
                        //跳转至Loading界面
                        startActivity(LoadingActivity.class);
                    }
                });
                loginTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private boolean checkLogin(){
        if (isTest){
            return true;
        }
        tv_prompt.setText("");
        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            tv_prompt.setText(R.string.prompt_no_account);
            return false;
        }
        if (!FormatCheckUtil.isPhone(phone)){
            tv_prompt.setText(R.string.prompt_error_phone);
            return false;
        }

        String loginPwd = et_login_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(loginPwd)){
            tv_prompt.setText(R.string.prompt_no_password);
            return false;
        }
        if (!FormatCheckUtil.isLetterDigit(loginPwd)){
            tv_prompt.setText(R.string.prompt_error_password);
            return false;
        }
        return true;
    }

    private void gotoRegister(){
        //跳转至注册界面
        startActivity(RegisterActivity.class);
    }

    private void gotoChangePwd(){
        //跳转至忘记密码界面
        startActivity(ForgotPwdActivity.class);
    }


}
