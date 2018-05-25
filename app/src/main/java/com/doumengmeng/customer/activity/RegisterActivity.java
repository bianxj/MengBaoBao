package com.doumengmeng.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseActivity;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.config.Constants;
import com.doumengmeng.customer.net.UrlAddressList;
import com.doumengmeng.customer.request.RequestCallBack;
import com.doumengmeng.customer.request.RequestTask;
import com.doumengmeng.customer.request.ResponseErrorCode;
import com.doumengmeng.customer.request.task.LoginTask;
import com.doumengmeng.customer.response.RegisterResponse;
import com.doumengmeng.customer.response.VCResponse;
import com.doumengmeng.customer.util.AppUtil;
import com.doumengmeng.customer.util.FormatCheckUtil;
import com.doumengmeng.customer.util.GsonUtil;

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

    private RelativeLayout rl_back;
    private Button bt_sure;
    private TextView bt_get_vc , tv_prompt;
    private EditText et_phone , et_vc , et_login_pwd;
    private LinearLayout ll_agreement;
    private CheckBox cb_agreement;
    private TextView tv_agreement;

    private int countDown = 0;

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
        if ( handler != null ){
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        bt_get_vc = findViewById(R.id.bt_get_vc);

        et_login_pwd = findViewById(R.id.et_login_pwd);
        et_phone = findViewById(R.id.et_phone);
        et_vc = findViewById(R.id.et_vc);
        tv_agreement = findViewById(R.id.tv_agreement);
        tv_agreement.getPaint().setUnderlineText(true);

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
        tv_agreement.setOnClickListener(listener);
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
                case R.id.tv_agreement:
                    Intent intent = new Intent(RegisterActivity.this,AgreementActivity.class);
                    startActivityForResult(intent,REQUEST_AGREEMENT);
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
        AppUtil.hideSoftInput(this,getWindow());
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
            if ( ResponseErrorCode.SUCCESS == response.getErrorId() ) {
                BaseApplication.getInstance().saveRegisterSession(response.getResult().getRegisterSesId());
                countDown = Constants.VC_OVER_TIME;
                handler.sendEmptyMessage(RegisterHandler.COUNT_DOWN);
            } else {
                bt_get_vc.setEnabled(true);
                tv_prompt.setText("验证码获取失败");
            }
        }
    };

    private RegisterHandler handler = new RegisterHandler(this);
    private static class RegisterHandler extends Handler{
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

    /**
     * 作者: 边贤君
     * 描述: 检测获取验证码信息是否完整
     * 返回:
     * 日期: 2018/1/8 9:26
     */
    private boolean checkVerificationCode(){
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
            object.put("checkCode",et_vc.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM,object.toString());
        map.put("registerSesId",BaseApplication.getInstance().getRegisterSession());
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
                tv_prompt.setText("");
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

    private void clearData(){
        et_login_pwd.setText("");
        et_phone.setText("");
        tv_prompt.setText("");
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
