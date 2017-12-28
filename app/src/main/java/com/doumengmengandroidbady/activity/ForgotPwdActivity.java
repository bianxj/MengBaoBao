package com.doumengmengandroidbady.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.util.FormatCheckUtil;
import com.doumengmengandroidbady.util.GsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/5.
 */
public class ForgotPwdActivity extends BaseActivity {

    public final static boolean isTest = false;

    private RelativeLayout rl_back;
    private Button bt_sure;
    private TextView bt_get_vc , tv_prompt;
    private EditText et_phone , et_vc , et_login_pwd;
    private LinearLayout ll_agreement;
    private CheckBox cb_agreement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        findView();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(getVerificationCodeTask);
        stopTask(changePwdTask);
        stopTask(loginTask);
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        bt_get_vc = findViewById(R.id.bt_get_vc);

        et_login_pwd = findViewById(R.id.et_login_pwd);
        et_phone = findViewById(R.id.et_phone);
        et_vc = findViewById(R.id.et_vc);

        tv_prompt = findViewById(R.id.tv_prompt);
        bt_sure = findViewById(R.id.bt_sure);

        ll_agreement = findViewById(R.id.ll_agreement);
        cb_agreement = findViewById(R.id.cb_agreement);
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        bt_get_vc.setOnClickListener(listener);
        bt_sure.setOnClickListener(listener);
        ll_agreement.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.bt_get_vc:
                    getVerificationCode();
                    break;
                case R.id.bt_sure:
                    changePwd();
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

    private RequestTask getVerificationCodeTask;
    private void getVerificationCode(){
        if ( checkVerificationCode() ) {
            try {
                getVerificationCodeTask = new RequestTask.Builder(getVerificationCodeCallBack).build();
                getVerificationCodeTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private String verificationCode;
    private RequestCallBack getVerificationCodeCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
        }

        @Override
        public String getUrl() {
            return UrlAddressList.mergeUrlAndParam(UrlAddressList.URL_RESET_PASSWORD_GET_VC,et_phone.getText().toString());
        }

        @Override
        public Context getContext() {
            return ForgotPwdActivity.this;
        }

        @Override
        public void onError(String result) {
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                verificationCode = res.optString("code");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String type() {
            return RequestCallBack.JSON;
        }
    };

    private boolean checkVerificationCode(){
        if (isTest){
            return true;
        }
        tv_prompt.setText("");
        String phone = et_phone.getText().toString().trim();
        if (!FormatCheckUtil.isPhone(phone) ){
            tv_prompt.setText(R.string.prompt_error_phone);
            return false;
        }
        return true;
    }

    private void changePwd(){
        if ( cb_agreement.isChecked() ) {
            if (checkChangePwd()) {
                try {
                    changePwdTask = new RequestTask.Builder(changePwdCallBack).build();
                    changePwdTask.execute();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        } else {
            Intent intent = new Intent(ForgotPwdActivity.this,AgreementActivity.class);
            startActivityForResult(intent,REQUEST_AGREEMENT);
        }
    }

    private boolean checkChangePwd(){
        if (isTest){
            return true;
        }
//        tv_prompt.setText("");
//        String phone = et_phone.getText().toString().trim();
//        if (TextUtils
//                .isEmpty(phone)){
//            tv_prompt.setText(R.string.prompt_no_account);
//            return false;
//        }
//        if (!FormatCheckUtil.isPhone(phone) ){
//            tv_prompt.setText(R.string.prompt_error_phone);
//            return false;
//        }
//
//        String vc = et_vc.getText().toString().trim();
//        if ( TextUtils.isEmpty(vc) ){
//            tv_prompt.setText(R.string.prompt_no_vc);
//            return false;
//        }
//
//        String pwd = et_login_pwd.getText().toString().trim();
//        if ( TextUtils.isEmpty(pwd) ){
//            tv_prompt.setText(R.string.prompt_no_password);
//            return false;
//        }
//
//        if ( FormatCheckUtil.isLetterDigit(pwd) ){
//            tv_prompt.setText(R.string.prompt_error_password);
//            return false;
//        }

        return true;
    }

    private RequestTask changePwdTask;

    private RequestCallBack changePwdCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
            //TODO
        }

        @Override
        public String getUrl() {
            JSONObject object = new JSONObject();
            try {
                object.put("accountMobile",et_phone.getText().toString().trim());
                object.put("loginPwd",et_login_pwd.getText().toString().trim());
                object.put("code", verificationCode);
                object.put("checkCode",et_vc.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return UrlAddressList.mergeUrlAndParam(UrlAddressList.URL_RESET_PASSWORD,object.toString());
        }

        @Override
        public Context getContext() {
            return ForgotPwdActivity.this;
        }

        @Override
        public void onError(String result) {
        }

        @Override
        public void onPostExecute(String result) {
            login();
        }

        @Override
        public String type() {
            return RequestCallBack.JSON;
        }
    };

    private RequestTask loginTask;
    private void login(){
        try {
            loginTask = new RequestTask.Builder(loginCallback).build();
            loginTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestCallBack loginCallback = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {
            JSONObject object = new JSONObject();
            try {
                object.put("accountMobile",et_phone.getText().toString().trim());
                object.put("loginPwd",et_login_pwd.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return UrlAddressList.mergeUrlAndParam(UrlAddressList.URL_LOGIN,object.toString());
        }

        @Override
        public Context getContext() {
            return ForgotPwdActivity.this;
        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                String sessionId = res.getString("SessionId");
                JSONObject user = res.getJSONObject("User");
                UserData userData = GsonUtil.getInstance().getGson().fromJson(user.toString(),UserData.class);
                userData.setSessionId(sessionId);

                BaseApplication.getInstance().saveUserData(userData);

                startActivity(LoadingActivity.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String type() {
            return JSON;
        }
    };

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

}
