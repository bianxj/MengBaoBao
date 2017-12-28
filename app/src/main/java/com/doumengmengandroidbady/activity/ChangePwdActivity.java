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
import com.doumengmengandroidbady.response.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/11.
 */
//TODO
public class ChangePwdActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title,tv_prompt;
    private EditText et_old_pwd , et_new_pwd , et_confirm_pwd;
    private Button bt_sure;

    private RequestTask changePwdTask;
    private UserData userData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        findView();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(changePwdTask);
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        tv_prompt = findViewById(R.id.tv_prompt);
        et_old_pwd = findViewById(R.id.et_old_pwd);
        et_new_pwd = findViewById(R.id.et_new_pwd);
        et_confirm_pwd = findViewById(R.id.et_confirm_pwd);
        bt_sure = findViewById(R.id.bt_sure);
    }

    private void initView(){
        userData = BaseApplication.getInstance().getUserData();

        tv_title.setText(R.string.change_pwd_name);
        tv_prompt.setText("");

        bt_sure.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.bt_sure:
                    changePwd();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void changePwd(){
        if ( checkData() ) {
            try {
                changePwdTask = new RequestTask.Builder(changePwdCallBack).build();
                changePwdTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private boolean checkData(){
        String newPwd = et_new_pwd.getText().toString().trim();
        String oldPwd = et_old_pwd.getText().toString().trim();
        String comfirmPwd = et_confirm_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(newPwd)){
//            tv_prompt.setText();
            //TODO
        }
        return true;
    }

    private RequestCallBack changePwdCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
        }

        @Override
        public String getUrl() {
            Map<String,String> map = new HashMap<>();
            JSONObject object = new JSONObject();
            try {
                object.put("oldPwd",et_old_pwd.getText().toString().trim());
                object.put("newPwd",et_new_pwd.getText().toString().trim());
                object.put("userId",userData.getUserid());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            map.put(UrlAddressList.PARAM,object.toString());
            map.put("sesId",userData.getSessionId());

            return UrlAddressList.mergUrlAndParam(UrlAddressList.URL_EIDT_PASSWORD,map);
        }

        @Override
        public Context getContext() {
            return ChangePwdActivity.this;
        }

        @Override
        public void onError(String result) {
            //TODO
        }

        @Override
        public void onPostExecute(String result) {
            //TODO
        }

        @Override
        public String type() {
            return JSON;
        }
    };

}
