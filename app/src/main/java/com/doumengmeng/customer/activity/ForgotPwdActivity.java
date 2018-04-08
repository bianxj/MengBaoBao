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
import com.doumengmeng.customer.net.UrlAddressList;
import com.doumengmeng.customer.request.RequestCallBack;
import com.doumengmeng.customer.request.RequestTask;
import com.doumengmeng.customer.request.ResponseErrorCode;
import com.doumengmeng.customer.request.task.LoginTask;
import com.doumengmeng.customer.response.ForgotPwdResponse;
import com.doumengmeng.customer.response.VCResponse;
import com.doumengmeng.customer.util.FormatCheckUtil;
import com.doumengmeng.customer.util.GsonUtil;
import com.doumengmeng.customer.util.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: 忘记密码
 * 创建日期: 2018/1/8 9:52
 */
public class ForgotPwdActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private Button bt_sure;
    private TextView bt_get_vc , tv_prompt;
    private EditText et_phone , et_vc , et_login_pwd;
    private LinearLayout ll_agreement;
    private CheckBox cb_agreement;

    private int countDown;

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
    /**
     * 作者: 边贤君
     * 描述: 获取验证码
     * 日期: 2018/1/8 9:52
     */
    private void getVerificationCode(){
        if ( checkVerificationCode() ) {
            try {
                getVerificationCodeTask = new RequestTask.Builder(this,getVerificationCodeCallBack)
                        .setUrl(UrlAddressList.URL_RESET_PASSWORD_GET_VC)
                        .setType(RequestTask.NO_PROMPT)
                        .setContent(buildVcContent()).build();
                getVerificationCodeTask.execute();
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
            disposeError(result);
        }

        @Override
        public void onPostExecute(String result) {
            VCResponse response = GsonUtil.getInstance().fromJson(result,VCResponse.class);
            BaseApplication.getInstance().saveForgetVc(response.getResult().getCode());

            countDown = 60;
            handler.sendEmptyMessage(ForgotHandler.COUNT_DOWN);
        }
    };

    private ForgotHandler handler = new ForgotHandler(this);
    private static class ForgotHandler extends Handler {
        public final static int COUNT_DOWN = 0x01;
        private WeakReference<ForgotPwdActivity> weakReference;

        public ForgotHandler(ForgotPwdActivity activity) {
            weakReference = new WeakReference<ForgotPwdActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( msg.what == COUNT_DOWN ){
                ForgotPwdActivity activity = weakReference.get();
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
     * 描述: 检测验证码
     * 日期: 2018/1/8 10:01
     */
    private boolean checkVerificationCode(){
        tv_prompt.setText("");
        String phone = et_phone.getText().toString().trim();
        if (!FormatCheckUtil.isPhone(phone) ){
            tv_prompt.setText(R.string.prompt_error_phone);
            return false;
        }
        return true;
    }

    private RequestTask changePwdTask;
    private void changePwd(){
        if (checkChangePwd()) {
            //检测用户协议是否勾选
            if ( cb_agreement.isChecked() ) {
                try {
                    changePwdTask = new RequestTask.Builder(this,changePwdCallBack)
                            .setUrl(UrlAddressList.URL_RESET_PASSWORD)
                            .setType(RequestTask.NO_PROMPT)
                            .setContent(buildChangePwdContent())
                            .build();
                    changePwdTask.execute();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            } else {
                //跳转至用户协议
                Intent intent = new Intent(ForgotPwdActivity.this,AgreementActivity.class);
                startActivityForResult(intent,REQUEST_AGREEMENT);
            }
        }
    }

    /**
     * 作者: 边贤君
     * 描述: 检测修改密码数据是否完全
     * 日期: 2018/1/8 9:58
     */
    private boolean checkChangePwd(){
        tv_prompt.setText("");
        String phone = et_phone.getText().toString().trim();
        if (TextUtils
                .isEmpty(phone)){
            tv_prompt.setText(R.string.prompt_no_account);
            return false;
        }
        if (!FormatCheckUtil.isPhone(phone) ){
            tv_prompt.setText(R.string.prompt_error_phone);
            return false;
        }

        String saveVc = BaseApplication.getInstance().getForgetVc();
        if ( TextUtils.isEmpty(saveVc) ){
            tv_prompt.setText(R.string.prompt_no_save_vc);

            return false;
        }

        String vc = et_vc.getText().toString().trim();
        if ( TextUtils.isEmpty(vc) ){
            tv_prompt.setText(R.string.prompt_no_vc);
            return false;
        }

        String pwd = et_login_pwd.getText().toString().trim();
        if ( TextUtils.isEmpty(pwd) ){
            tv_prompt.setText(R.string.prompt_no_password);
            return false;
        }

        if ( !FormatCheckUtil.isLetterDigit(pwd) ){
            tv_prompt.setText(R.string.prompt_error_password);
            return false;
        }
        return true;
    }

    private Map<String, String> buildChangePwdContent() {
        JSONObject object = new JSONObject();
        try {
            object.put("accountMobile",et_phone.getText().toString().trim());
            object.put("loginPwd",et_login_pwd.getText().toString().trim());
            object.put("code", BaseApplication.getInstance().getForgetVc());
            object.put("checkCode",et_vc.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM,object.toString());
        return map;
    }

    private final RequestCallBack changePwdCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
        }

        @Override
        public void onError(String result) {
            disposeError(result);
        }

        @Override
        public void onPostExecute(String result) {
            ForgotPwdResponse response = GsonUtil.getInstance().fromJson(result,ForgotPwdResponse.class);
            if ( 1 == response.getResult().getIsEditPwd() ) {
                login();
            } else {
                //TODO
                tv_prompt.setText("密码修改失败");
            }
        }

    };

    //---------------------------------登录------------------------------------------------------
    private LoginTask loginTask;
    private void login(){
        try {
            String accountMobile = et_phone.getText().toString().trim();
            String loginPwd = et_login_pwd.getText().toString().trim();
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
                    startActivity(LoadingActivity.class);
                }
            });
            loginTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    //---------------------------------使用协议-----------------------------------------------------
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

    /**
     * 作者: 边贤君
     * 描述: 错误提示
     * 日期: 2018/1/8 9:57
     */
    private void disposeError(String result){
        int errorCode = ResponseErrorCode.getErrorCode(result);
        String errorMsg = ResponseErrorCode.getErrorMsg(errorCode);
        if ( ResponseErrorCode.ERROR_LOGIN_ROLE_NOT_EXIST == errorCode ){
            //用户不存在跳转至注册界面
            MyDialog.showPromptDialog(this, errorMsg, new MyDialog.PromptDialogCallback() {
                @Override
                public void sure() {
                    startActivity(RegisterActivity.class);
                    back();
                }
            });
        } else {
            tv_prompt.setText(errorMsg);
        }
    }

}
