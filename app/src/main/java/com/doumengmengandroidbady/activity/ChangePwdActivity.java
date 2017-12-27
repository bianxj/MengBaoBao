package com.doumengmengandroidbady.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        findView();
        initView();
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
        tv_title.setText(R.string.change_pwd_name);
        tv_prompt.setText("");

        bt_sure.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);

        et_old_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if ( actionId == EditorInfo.IME_ACTION_GO){
//                    Toast.makeText(ChangePwdActivity.this,"GO",Toast.LENGTH_SHORT).show();
//                }
//                if ( actionId == EditorInfo.IME_ACTION_DONE){
//                    Toast.makeText(ChangePwdActivity.this,"DONE",Toast.LENGTH_SHORT).show();
//                }
//                if ( actionId == EditorInfo.IME_ACTION_NEXT){
//                    Toast.makeText(ChangePwdActivity.this,"NEXT",Toast.LENGTH_SHORT).show();
//                }
                return false;
            }
        });
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
        try {
            buildChangePwdTask().execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestTask buildChangePwdTask() throws Throwable {
        changePwdTask = new RequestTask.Builder(changePwdCallBack).build();
        return changePwdTask;
    }

    private RequestCallBack changePwdCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
            //TODO
        }

        @Override
        public String getUrl() {
            //TODO
            return null;
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
            back();
        }

        @Override
        public String type() {
            return null;
        }
    };

}
