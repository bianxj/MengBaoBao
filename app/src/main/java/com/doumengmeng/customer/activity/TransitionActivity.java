package com.doumengmeng.customer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseActivity;

public class TransitionActivity extends BaseActivity {

    public final static String LAUNCHER_PARAM = "launcher_param";
    public final static String LAUNCHER_LOGIN = "login";
    public final static String LAUNCHER_REGISTER = "register";
    public final static String LAUNCHER_LOADING = "loading";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        Intent intent = getIntent();
//        String param = intent.getStringExtra(LAUNCHER_PARAM);
//        if ( LAUNCHER_LOGIN.equals(param)){
//            startActivity(LoginActivity.class);
//        } else if ( LAUNCHER_REGISTER.equals(param) ) {
//            startActivity(RegisterActivity.class);
//        } else {
//            startActivity(LoadingActivity.class);
//        }
    }
}
