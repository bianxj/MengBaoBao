package com.doumengmengandroidbady.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.doumengmengandroidbady.config.Config;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.request.ResponseErrorCode;
import com.doumengmengandroidbady.request.task.LoginTask;
import com.doumengmengandroidbady.response.RegisterResponse;
import com.doumengmengandroidbady.response.VCResponse;
import com.doumengmengandroidbady.util.FormatCheckUtil;
import com.doumengmengandroidbady.util.GsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述
 * 创建日期: 2018/1/8 9:23
 * 萌宝宝用户注册界面
 */
public class RegisterActivity extends BaseActivity {
    private static final boolean isTest = Config.isTest;

    private RelativeLayout rl_back;
    private Button bt_sure;
    private TextView bt_get_vc , tv_prompt;
    private EditText et_phone , et_vc , et_login_pwd;
    private LinearLayout ll_agreement;
    private CheckBox cb_agreement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findView();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(getVerificationTask);
        stopTask(registerTask);
        if ( loginTask != null ) {
            stopTask(loginTask.getTask());
        }
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

    private final View.OnClickListener listener = new View.OnClickListener() {
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
                    register();
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


    private RequestTask getVerificationTask;
    /**
     * 作者: 边贤君
     * 描述: 获取验证码
     * 日期: 2018/1/8 9:24
     */
    private void getVerificationCode(){
        if ( checkVerificationCode() ) {
            try {
                getVerificationTask = new RequestTask.Builder(this,getVerificationCodeCallBack)
                        .setUrl(UrlAddressList.URL_GET_VC)
                        .setType(RequestTask.NO_PROMPT)
                        .setContent(buildVcContent())
                        .build();
                getVerificationTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private Map<String, String> buildVcContent() {
        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM,et_phone.getText().toString());
        return map;
    }

    private final RequestCallBack getVerificationCodeCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
        }

        @Override
        public void onError(String result) {
            tv_prompt.setText(ResponseErrorCode.getErrorMsg(result));
        }

        @Override
        public void onPostExecute(String result) {
            VCResponse response = GsonUtil.getInstance().fromJson(result,VCResponse.class);
            BaseApplication.getInstance().saveRegisterVc(response.getResult().getCode());
        }
    };

    /**
     * 作者: 边贤君
     * 描述: 检测获取验证码信息是否完整
     * 返回:
     * 日期: 2018/1/8 9:26
     */
    private boolean checkVerificationCode(){
        if (isTest){
            return true;
        }
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

    private RequestTask registerTask;
    /**
     * 作者: 边贤君
     * 描述: 注册
     * 日期: 2018/1/8 9:25
     */
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
                //跳转至用户协议界面
                Intent intent = new Intent(RegisterActivity.this,AgreementActivity.class);
                startActivityForResult(intent,REQUEST_AGREEMENT);
            }
        }
    }

    private Map<String, String> buildRegisterContent() {
        JSONObject object = new JSONObject();
        try {
            object.put("accountMobile",et_phone.getText().toString().trim());
            object.put("loginPwd",et_login_pwd.getText().toString().trim());
            object.put("code", BaseApplication.getInstance().getRegisterVc());
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
            RegisterResponse response = GsonUtil.getInstance().fromJson(result,RegisterResponse.class);
            if ( 1 == response.getResult().getIsSuccess() ){
                login();
            } else {
                //TODO
            }
        }
    };

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

    /**
     * 作者: 边贤君
     * 描述: 检测激活数据是否完整
     * 返回:
     * 日期: 2018/1/8 9:27
     */
    private boolean checkRegisterData(){
        if (isTest){
            return true;
        }
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
