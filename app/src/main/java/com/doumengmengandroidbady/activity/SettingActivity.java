package com.doumengmengandroidbady.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.util.AppUtil;

/**
 * 作者: 边贤君
 * 描述: 设置
 * 创建日期: 2018/1/8 10:47
 */
public class SettingActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title,tv_version;
    private RelativeLayout rl_change_pwd , rl_agreement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findView();
        initView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        tv_version = findViewById(R.id.tv_version);
        rl_change_pwd = findViewById(R.id.rl_change_pwd);
        rl_agreement = findViewById(R.id.rl_agreement);
    }

    private void initView(){
        tv_title.setText(R.string.setting);
        String version = null;
        try {
            version = AppUtil.getVersionName(this);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tv_version.setText("版本号"+version);
        rl_back.setOnClickListener(listener);
        rl_change_pwd.setOnClickListener(listener);
        rl_agreement.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_change_pwd:
                    startActivity(ChangePwdActivity.class);
                    break;
                case R.id.rl_agreement:
                    Intent intent = new Intent(SettingActivity.this,AgreementActivity.class);
                    intent.putExtra(AgreementActivity.HIDE_BOTTOM,true);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

}
