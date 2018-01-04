package com.doumengmengandroidbady.activity;

import android.content.Context;
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
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.request.ResponseErrorCode;
import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.util.FormatCheckUtil;
import com.doumengmengandroidbady.util.GsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/5.
 */
public class LoginActivity extends BaseActivity {

    private static boolean isTest = false;

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
        stopTask(loginTask);
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

    private RequestTask loginTask = null;
    private void login(){
        if ( checkLogin() ){
            try {
                loginTask = new RequestTask.Builder(loginCallBack).build();
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

    private RequestCallBack loginCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
        }

        @Override
        public String getUrl() {
            return UrlAddressList.URL_LOGIN;
        }

        @Override
        public Context getContext() {
            return LoginActivity.this;
        }

        @Override
        public Map<String, String> getContent() {
            JSONObject object = new JSONObject();
            try {
                object.put("accountMobile",et_phone.getText().toString().trim());
                object.put("loginPwd",et_login_pwd.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Map<String,String> map = new HashMap<>();
            map.put(UrlAddressList.PARAM,object.toString());
            return map;
        }

        @Override
        public void onError(String result) {
            int errorCode = ResponseErrorCode.getErrorCode(result);
            String errorMsg = ResponseErrorCode.getErrorMsg(errorCode);
            tv_prompt.setText(errorMsg);
        }

        @Override
        public void onPostExecute(String result) {
            if ( isTest ) {
                startActivity(LoadingActivity.class);
            } else {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject res = object.getJSONObject("result");
                    String sessionId = res.getString("SessionId");
                    JSONObject user = res.getJSONObject("User");
                    UserData userData = GsonUtil.getInstance().getGson().fromJson(user.toString(), UserData.class);
                    userData.setSessionId(sessionId);

                    BaseApplication.getInstance().saveUserData(userData);

                    startActivity(LoadingActivity.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public String type() {
            return RequestCallBack.JSON_NO_PROMPT;
        }
    };

    private void gotoRegister(){
        startActivity(RegisterActivity.class);
    }

    private void gotoChangePwd(){
        startActivity(ForgotPwdActivity.class);
    }


}
