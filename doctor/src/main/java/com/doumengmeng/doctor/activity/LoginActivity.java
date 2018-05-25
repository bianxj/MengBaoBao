package com.doumengmeng.doctor.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.request.task.LoginTask;
import com.doumengmeng.doctor.util.AppUtil;
import com.doumengmeng.doctor.util.FormatCheckUtil;
import com.doumengmeng.doctor.util.MyDialog;

/**
 * 作者: 边贤君
 * 描述: 登录页面
 * 创建日期: 2018/1/8 9:50
 */
public class LoginActivity extends BaseActivity {

    private View v_status_bar;

    private EditText et_phone,et_login_pwd;
    private TextView tv_prompt;
    private Button bt_sure;
    private TextView tv_fast_register,tv_forgot_pwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        findView();
    }

    private void findView(){
        v_status_bar = findViewById(R.id.v_status_bar);
        v_status_bar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtil.getStatusBarHeight(this)));

        et_phone = findViewById(R.id.et_phone);
        et_login_pwd = findViewById(R.id.et_login_pwd);

        tv_prompt = findViewById(R.id.tv_prompt);
        bt_sure = findViewById(R.id.bt_sure);
        tv_fast_register = findViewById(R.id.tv_fast_register);
        tv_forgot_pwd = findViewById(R.id.tv_forgot_pwd);
        initView();
    }

    private void initView(){
        bt_sure.setOnClickListener(listener);
        tv_fast_register.setOnClickListener(listener);
        tv_forgot_pwd.setOnClickListener(listener);
        tv_fast_register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_forgot_pwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
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

    private LoginTask loginTask = null;
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
                        //TODO
                        tv_prompt.setText(ResponseErrorCode.getErrorMsg(result));
                    }

                    @Override
                    public void onPostExecute(String result) {
                        //跳转至Loading界面
                        if (BaseApplication.getInstance().isAbnormalExit()){
                            startActivity(LoadingActivity.class);
                            finish();
                        } else if (BaseApplication.getInstance().isToExamine()){
                            MyDialog.showPromptDialog(LoginActivity.this,"请等待审核\n" +
                                    " 审核通过后将会以短信形式通知您",R.string.sure,null);
                        } else {
                            startActivity(LoadingActivity.class);
                            finish();
                        }
//                        if (BaseApplication.getInstance().isToExamine()){
//                            startActivity(LoadingActivity.class);
//                        } else {
//                            MyDialog.showPromptDialog(LoginActivity.this,"请等待审核\n" +
//                                    " 审核通过后将会以短信形式通知您",R.string.sure,null);
//                        }
                    }
                });
                loginTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private boolean checkLogin(){
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

    private void clearData(){
        tv_prompt.postDelayed(new Runnable() {
            @Override
            public void run() {
                et_login_pwd.setText("");
                et_phone.setText("");
                tv_prompt.setText("");
            }
        },300);
    }

    private void gotoRegister(){
        startActivity(RegisterActivity.class);
        clearData();
    }

    private void gotoChangePwd(){
        startActivity(ForgotPwdActivity.class);
        clearData();
    }



}
