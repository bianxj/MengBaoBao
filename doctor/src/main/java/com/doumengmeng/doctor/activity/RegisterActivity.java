package com.doumengmeng.doctor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.request.task.LoginTask;
import com.doumengmeng.doctor.response.RegisterResponse;
import com.doumengmeng.doctor.response.VCResponse;
import com.doumengmeng.doctor.util.AppUtil;
import com.doumengmeng.doctor.util.FormatCheckUtil;
import com.doumengmeng.doctor.util.GsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述
 * 创建日期: 2018/1/8 9:23
 * 萌宝宝用户注册界面
 */
public class RegisterActivity extends BaseActivity {

    private View v_status_bar;
    private Button bt_sure;
    private TextView bt_get_vc , tv_prompt , tv_agreement;
    private TextView tv_fast_login , tv_forgot_pwd;
    private EditText et_phone , et_vc , et_login_pwd;
    private LinearLayout ll_agreement;
    private CheckBox cb_agreement;

    private int countDown = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(registerTask);
        stopTask(getVerifyCodeTask);
        if ( loginTask != null ) {
            stopTask(loginTask.getTask());
        }
        clearHandler(handler);
    }

    private void findView(){
        initStatusBar();
        initContentView();
    }

    private void initStatusBar(){
        v_status_bar = findViewById(R.id.v_status_bar);
        v_status_bar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtil.getStatusBarHeight(this)));
    }

    private void initContentView(){
        et_login_pwd = findViewById(R.id.et_login_pwd);
        et_phone = findViewById(R.id.et_phone);
        et_vc = findViewById(R.id.et_vc);
        bt_get_vc = findViewById(R.id.bt_get_vc);
        bt_sure = findViewById(R.id.bt_sure);
        tv_prompt = findViewById(R.id.tv_prompt);
        tv_agreement = findViewById(R.id.tv_agreement);
        ll_agreement = findViewById(R.id.ll_agreement);
        cb_agreement = findViewById(R.id.cb_agreement);
        tv_fast_login = findViewById(R.id.tv_fast_login);
        tv_forgot_pwd = findViewById(R.id.tv_forgot_pwd);

        tv_fast_login.getPaint().setUnderlineText(true);
        tv_fast_login.setOnClickListener(listener);
        tv_forgot_pwd.getPaint().setUnderlineText(true);
        tv_forgot_pwd.setOnClickListener(listener);
        bt_get_vc.setOnClickListener(listener);
        bt_sure.setOnClickListener(listener);
        ll_agreement.setOnClickListener(listener);
        tv_agreement.setOnClickListener(listener);
        tv_agreement.getPaint().setUnderlineText(true);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bt_get_vc:
                    getVerifyCode();
                    break;
                case R.id.bt_sure:
                    register();
                    break;
                case R.id.ll_agreement:
                    clickAgreement();
                    break;
                case R.id.tv_agreement:
                    goAgreementActivity();
                    break;
                case R.id.tv_fast_login:
                    goLoginActivity();
                    break;
                case R.id.tv_forgot_pwd:
                    goForgotPwdActivity();
                    break;
            }
        }
    };

    private RequestTask getVerifyCodeTask;
    private void getVerifyCode(){
        if ( checkVerifyCodeData() ) {
            try {
                getVerifyCodeTask = new RequestTask.Builder(this,getVerificationCodeCallBack)
                        .setUrl(UrlAddressList.URL_GET_VC)
                        .setType(RequestTask.NO_PROMPT)
                        .setContent(buildVerifyCodeContent())
                        .build();
                getVerifyCodeTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private boolean checkVerifyCodeData(){
        tv_prompt.setText("");
        String phone = et_phone.getText().toString().trim();
        if ( TextUtils.isEmpty(phone) ){
            tv_prompt.setText(R.string.prompt_no_account);
            return false;
        }
        if (!FormatCheckUtil.isPhone(phone) ){
            tv_prompt.setText(R.string.prompt_error_phone);
            return false;
        }
        return true;
    }

    private Map<String, String> buildVerifyCodeContent() {
        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM,et_phone.getText().toString());
        return map;
    }

    private final RequestCallBack getVerificationCodeCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
            bt_get_vc.setEnabled(false);
        }

        @Override
        public void onError(String result) {
            bt_get_vc.setEnabled(true);
            tv_prompt.setText(ResponseErrorCode.getErrorMsg(result));
        }

        @Override
        public void onPostExecute(String result) {
            VCResponse response = GsonUtil.getInstance().fromJson(result,VCResponse.class);
//            BaseApplication.getInstance().saveRegisterVc(response.getResult().getCode());
            if ( ResponseErrorCode.SUCCESS == response.getErrorId() ) {
                countDown = 60;
                handler.sendEmptyMessage(RegisterHandler.COUNT_DOWN);
            } else {
                bt_get_vc.setEnabled(true);
                tv_prompt.setText("验证码获取失败");
            }
        }
    };

    private RequestTask registerTask;
    private void register(){
        if ( checkRegisterData() ){
            //检测是否勾选用户协议
            if ( cb_agreement.isChecked() ){
                //进入激活流程
                try {
                    registerTask = new RequestTask.Builder(this,registerCallBack)
                            .setUrl(UrlAddressList.URL_REGISTER)
                            .setType(RequestTask.NO_PROMPT)
                            .setContent(buildRegisterContent())
                            .build();
                    registerTask.execute();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            } else {
                goAgreementActivity();
            }
        }
    }

    private boolean checkRegisterData(){
        tv_prompt.setText("");
        String phone = et_phone.getText().toString().trim();

        if ( TextUtils.isEmpty(phone) ){
            tv_prompt.setText(R.string.prompt_no_account);
            return false;
        }

        if ( !FormatCheckUtil.isPhone(phone) ){
            tv_prompt.setText(R.string.prompt_error_phone);
            return false;
        }

        String vc = et_vc.getText().toString().trim();
        if (TextUtils.isEmpty(vc)){
            tv_prompt.setText(R.string.prompt_no_vc);
            return false;
        }

        String password = et_login_pwd.getText().toString().trim();
        if ( TextUtils.isEmpty(password) ){
            tv_prompt.setText(R.string.prompt_no_password);
            return false;
        }

        if ( !FormatCheckUtil.isLetterDigit(password) ){
            tv_prompt.setText(R.string.prompt_error_password);
            return false;
        }
        return true;
    }

    private Map<String, String> buildRegisterContent() {
        JSONObject object = new JSONObject();
        try {
            //TODO
            object.put("doctorPhone",et_phone.getText().toString().trim());
            object.put("loginPwd",et_login_pwd.getText().toString().trim());
//            object.put("code", BaseApplication.getInstance().getRegisterVc());
            object.put("checkCode",et_vc.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM,object.toString());
        return map;
    }

    private final RequestCallBack registerCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {}

        @Override
        public void onError(String result) {
            tv_prompt.setText(ResponseErrorCode.getErrorMsg(result));
        }

        @Override
        public void onPostExecute(String result) {
            //TODO
            RegisterResponse response = GsonUtil.getInstance().fromJson(result,RegisterResponse.class);
            if ( 1 == response.getResult().getIsSuccess() ){
                login();
            } else {
                tv_prompt.setText("");
            }
        }
    };

    private void goAgreementActivity(){
        //跳转至用户协议界面
        Intent intent = new Intent(RegisterActivity.this,AgreementActivity.class);
        startActivityForResult(intent,REQUEST_AGREEMENT);
    }

    private LoginTask loginTask;
    /**
     * 作者: 边贤君
     * 描述: 登录
     * 日期: 2018/1/8 9:26
     */
    private void login(){
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
                    //登录成功后,进入Loading页面
                    startActivity(LoadingActivity.class);
                }
            });
            loginTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void goLoginActivity(){
        startActivity(LoginActivity.class);
    }

    private void goForgotPwdActivity(){
        startActivity(ForgotPwdActivity.class);
    }

    private void clickAgreement(){
        cb_agreement.setChecked(!cb_agreement.isChecked());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST_AGREEMENT ){
            if ( resultCode == Activity.RESULT_OK){
                cb_agreement.setChecked(true);
            }
        }
    }

    private RegisterHandler handler = new RegisterHandler(this);
    private static class RegisterHandler extends Handler {
        public final static int COUNT_DOWN = 0x01;
        private WeakReference<RegisterActivity> weakReference;

        public RegisterHandler(RegisterActivity activity) {
            weakReference = new WeakReference<RegisterActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( msg.what == COUNT_DOWN ){
                RegisterActivity activity = weakReference.get();
                if ( activity.countDown <= 0 ){
                    activity.bt_get_vc.setText(activity.getString(R.string.replay));
                    activity.bt_get_vc.setEnabled(true);
                    removeMessages(COUNT_DOWN);
                } else {
                    activity.bt_get_vc.setText(activity.countDown+activity.getString(R.string.second_chinese));
                    activity.countDown--;
                    sendEmptyMessageDelayed(COUNT_DOWN,1000);
                }
            }
        }
    }

}
