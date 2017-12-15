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
import com.doumengmengandroidbady.config.Config;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.BaseAsyncTask;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.util.FormatCheckUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/5.
 */
public class RegisterActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private Button bt_sure;
    private TextView bt_get_vc , tv_prompt;
    private EditText et_phone , et_vc , et_login_pwd;
    private LinearLayout ll_agreement;
    private CheckBox cb_agreement;

    public static final int REQUEST_AGREEMENT = 0x01;


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

    private void configView(){
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
        if ( checkVcData() ) {
            new BaseAsyncTask(new RequestCallBack() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public String getUrl() {
                    return UrlAddressList.URL_GET_VC+"&paramStr="+et_phone.getText().toString();
                }

                @Override
                public void disposeResponseInThread(String result) {}

                @Override
                public void onPostExecute(String result) {

                }
            }).execute();
        }
    }

    private boolean checkVcData(){
        if (Config.isTest){
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

    private void sure(){
        if ( checkSureData() ){
            if ( cb_agreement.isChecked() ){
                new BaseAsyncTask(new RequestCallBack() {
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
                            object.put("code",et_vc.getText().toString().trim());
                            object.put("checkCode",et_vc.getText().toString().trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return UrlAddressList.REGISTER_CHECT+"&paramStr="+object.toString();
                    }

                    @Override
                    public void disposeResponseInThread(String result) {}

                    @Override
                    public void onPostExecute(String result) {
                        startActivity(LoadingActivity.class);
                    }
                }).execute();
            } else {
                Intent intent = new Intent(RegisterActivity.this,AgreementActivity.class);
                startActivityForResult(intent,REQUEST_AGREEMENT);
            }
        }
    }

    private boolean checkSureData(){
        if (Config.isTest){
            return true;
        }
        tv_prompt.setText("");
        String phone = et_phone.getText().toString().trim();
        String vc = et_vc.getText().toString().trim();
        String password = et_login_pwd.getText().toString().trim();

        if ( !FormatCheckUtil.isPhone(phone) ){
            tv_prompt.setText(R.string.prompt_error_phone);
            return false;
        }

        if (TextUtils.isEmpty(vc)){
            tv_prompt.setText(R.string.prompt_no_vc);
            return false;
        }

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
